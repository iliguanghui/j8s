package com.lgypro.j8s.framework.plugins.tainttoleration;

import com.lgypro.j8s.api.core.v1.*;
import com.lgypro.j8s.componenthelpers.scheduling.corev1.Helpers;
import com.lgypro.j8s.context.Context;
import com.lgypro.j8s.framework.*;
import com.lgypro.j8s.framework.plugins.helper.Helper;
import com.lgypro.j8s.framework.plugins.names.Names;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

public class TaintToleration implements FilterPlugin, PreScorePlugin, ScorePlugin, ScoreExtensions, EnqueueExtensions {
    public static String name = Names.TaintToleration;

    /**
     * preScoreStateKey is the key in CycleState to TaintToleration pre-computed data for Scoring.
     */
    public static final String preScoreStateKey = "PreScore" + name;
    /**
     * ErrReasonNotMatch is the Filter reason status when not matching.
     */
    public static final String errorReasonNotMatch = "node(s) had taints that the pod didn't tolerate";

    private final Handle handle;

    public TaintToleration(Handle handle) {
        this.handle = handle;
    }

    @Override
    public List<ClusterEvent> eventsToRegister() {
        return List.of(new ClusterEvent(GroupVersionKind.Node,
                ActionType.fromValue(ActionType.Add.value() | ActionType.Update.value()), null));
    }

    @Override
    public Status filter(Context context, CycleState state, Pod pod, NodeInfo nodeInfo) {
        Node node = nodeInfo.getNode();
        if (node == null) {
            return Status.builder().withCode(Code.ERROR).withReason("invalid nodeInfo").build();
        }
        Taint taint = Helpers.findMatchingUntoleratedTaint(node.getSpec().getTaints(), pod.getSpec().getTolerations(), new Predicate<Taint>() {
            @Override
            public boolean test(Taint taint) {
                return taint.getEffect().equals(TaintEffect.NoSchedule) || taint.getEffect().equals(TaintEffect.NoExecute);
            }
        });
        if (taint == null) {
            return Status.builder().withCode(Code.SUCCESS).build();
        } else {
            return Status.builder().withCode(Code.UNSCHEDULABLEANDUNRESOLVABLE)
                    .withReason(String.format("node(s) had untolerated taint {%s: %s}", taint.getKey(), taint.getValue())).build();
        }
    }

    @Override
    public String name() {
        return name;
    }

    /**
     * PreScore builds and writes cycle state used by Score and NormalizeScore
     */
    @Override
    public Status preScore(Context context, CycleState state, Pod pod, List<Node> nodes) {
        if (nodes == null || nodes.isEmpty()) {
            return Status.builder().withCode(Code.ERROR).build();
        }
        List<Toleration> tolerationPreferNoSchedule = getAllTolerationPreferNoSchedule(pod.getSpec().getTolerations());
        PreScoreState preScoreState = new PreScoreState(tolerationPreferNoSchedule);
        state.write(preScoreStateKey, preScoreState);
        return Status.builder().withCode(Code.SUCCESS).build();
    }

    /**
     * Score invoked at the Score extension point.
     */
    @Override
    public Object[] score(Context context, CycleState state, Pod pod, String nodeName) {
        NodeInfo nodeInfo = handle.snapshotSharedLister().nodeInfos().get(nodeName);
        if (nodeInfo == null) {
            return new Object[]{0,
                    Status.builder().withCode(Code.ERROR).withReason(String.format("getting node %s failed", nodeName)).build()};
        }
        Node node = nodeInfo.getNode();
        PreScoreState preScoreState = getPreScoreState(state);
        int score = countIntolerableTaintsPreferNoSchedule(node.getSpec().getTaints(), preScoreState.getTolerationsPreferNoSchedule());

        return new Object[]{
                score,
                Status.builder().withCode(Code.SUCCESS).build()
        };
    }

    @Override
    public ScoreExtensions scoreExtensions() {
        return this;
    }

    static List<Toleration> getAllTolerationPreferNoSchedule(List<Toleration> tolerations) {
        List<Toleration> result = new ArrayList<>();
        for (Toleration toleration : tolerations) {
            if (toleration.getEffect() == null || toleration.getEffect().equals(TaintEffect.PreferNoSchedule)) {
                result.add(toleration);
            }
        }
        return result;
    }

    public static TaintToleration newInstance(Object object, Handle handle) {
        return new TaintToleration(handle);
    }

    static PreScoreState getPreScoreState(CycleState state) {
        return (PreScoreState) state.read(preScoreStateKey);
    }

    static int countIntolerableTaintsPreferNoSchedule(List<Taint> taints, List<Toleration> tolerations) {
        int intolerableTaints = 0;
        for (Taint taint : taints) {
            /*
            check only on taints that have effect PreferNoSchedule
             */
            if (!taint.getEffect().equals(TaintEffect.PreferNoSchedule)) {
                continue;
            }
            if (!Helpers.tolerationsTolerateTaint(tolerations, taint)) {
                intolerableTaints++;
            }
        }
        return intolerableTaints;
    }

    @Override
    public Status normalizeScore(Context ctx, CycleState state, Pod pod, List<NodeScore> scores) {
        return Helper.defaultNormalizeScore(ScorePlugin.maxNodeScore, true, scores);
    }
}
