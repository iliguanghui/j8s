package com.lgypro.j8s.framework.plugins.nodename;

import com.lgypro.j8s.api.core.v1.Pod;
import com.lgypro.j8s.context.Context;
import com.lgypro.j8s.framework.*;
import com.lgypro.j8s.framework.plugins.names.Names;
import org.apache.logging.log4j.util.Strings;

import java.util.List;

public class NodeName implements FilterPlugin, EnqueueExtensions {

    public static final String name = Names.NodeName;

    // ErrReason returned when node name doesn't match.
    public static final String errorReason = "node(s) didn't match the requested node name";

    @Override
    public List<ClusterEvent> eventsToRegister() {
        return List.of(new ClusterEvent(GroupVersionKind.Node,
                ActionType.fromValue(ActionType.Add.value() | ActionType.Update.value()), null));
    }

    @Override
    public Status filter(Context context, CycleState state, Pod pod, NodeInfo nodeInfo) {
        if (!fits(pod, nodeInfo)) {
            return Status.builder()
                    .withCode(Code.UNSCHEDULABLEANDUNRESOLVABLE)
                    .withReason(errorReason).build();
        }
        return Status.builder().withCode(Code.SUCCESS).build();
    }

    /**
     * fits actually checks if the pod fits the node.
     */
    public boolean fits(Pod pod, NodeInfo nodeInfo) {
        String nodeName = pod.getSpec().getNodeName();
        return Strings.isBlank(nodeName) || nodeName.equals(nodeInfo.getNode().getObjectMeta().getName());
    }

    @Override
    public String name() {
        return name;
    }

    public static NodeName newInstance(Object object, Handle handle) {
        return new NodeName();
    }
}
