package com.lgypro.j8s.framework.plugins.tainttoleration;

import com.lgypro.j8s.api.core.v1.*;
import com.lgypro.j8s.apis.meta.v1.ObjectMeta;
import com.lgypro.j8s.context.BackgroundContext;
import com.lgypro.j8s.context.Context;
import com.lgypro.j8s.framework.CycleState;
import com.lgypro.j8s.framework.NodeScore;
import com.lgypro.j8s.framework.ScorePlugin;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;

class TaintTolerationTest {
    static Node nodeWithTaints(String nodeName, List<Taint> taints) {
        return Node.builder()
                .setObjectMeta(ObjectMeta.builder()
                        .setName(nodeName)
                        .build())
                .setNodeSpec(NodeSpec.builder()
                        .setTaints(taints)
                        .build())
                .build();
    }

    static Pod PodWithTolerations(String podName, List<Toleration> tolerations) {
        return Pod.builder()
                .setObjectMeta(ObjectMeta.builder()
                        .setName(podName)
                        .build())
                .setSpec(PodSpec.builder()
                        .setTolerations(tolerations)
                        .build())
                .build();
    }

    @ParameterizedTest
    @MethodSource
    void testTaintTolerationScore(String description, Pod pod, List<Node> nodes, List<NodeScore> expectedScores) {
        Context context = new BackgroundContext();
        CycleState state = new CycleState();
    }

    static Stream<Arguments> testTaintTolerationScore() {
        return Stream.of(
                // basic test case
                Arguments.of(
                        "node with taints tolerated by the pod, gets a higher score than those node with intolerable taints",
                        PodWithTolerations("pod1", List.of(
                                Toleration.builder()
                                        .setKey("foo")
                                        .setOperator(TolerationOperator.Equal)
                                        .setValue("bar")
                                        .setEffect(TaintEffect.PreferNoSchedule)
                                        .build()

                        )),
                        List.of(
                                nodeWithTaints("nodeA", List.of(
                                        Taint.builder()
                                                .setKey("foo")
                                                .setValue("bar")
                                                .setEffect(TaintEffect.PreferNoSchedule)
                                                .build()
                                )),
                                nodeWithTaints("nodeB", List.of(
                                        Taint.builder()
                                                .setKey("foo")
                                                .setValue("blah")
                                                .setEffect(TaintEffect.PreferNoSchedule)
                                                .build()
                                ))
                        ),
                        List.of(
                                NodeScore.builder()
                                        .setName("nodeA")
                                        .setScore(ScorePlugin.maxNodeScore)
                                        .build(),
                                NodeScore.builder()
                                        .setName("nodeB")
                                        .setScore(0)
                                        .build()
                        )
                ),
                // the count of taints that are tolerated by pod, does not matter
                Arguments.of(
                        "the nodes that all of their taints are tolerated by the pod, get the same score, no matter how many tolerable taints a node has",
                        PodWithTolerations("pod1", List.of(
                                Toleration.builder()
                                        .setKey("cpu-type")
                                        .setOperator(TolerationOperator.Equal)
                                        .setValue("arm64")
                                        .setEffect(TaintEffect.PreferNoSchedule)
                                        .build(),
                                Toleration.builder()
                                        .setKey("disk-type")
                                        .setOperator(TolerationOperator.Equal)
                                        .setValue("ssd")
                                        .setEffect(TaintEffect.PreferNoSchedule)
                                        .build()
                        )),
                        List.of(
                                nodeWithTaints("nodeA",
                                        Collections.emptyList()),
                                nodeWithTaints("nodeB", List.of(
                                        Taint.builder()
                                                .setKey("cpu-type")
                                                .setValue("arm64")
                                                .setEffect(TaintEffect.PreferNoSchedule)
                                                .build()
                                )),
                                nodeWithTaints("nodeC", List.of(
                                        Taint.builder()
                                                .setKey("cpu-type")
                                                .setValue("arm64")
                                                .setEffect(TaintEffect.PreferNoSchedule)
                                                .build(),
                                        Taint.builder()
                                                .setKey("disk-type")
                                                .setValue("ssd")
                                                .setEffect(TaintEffect.PreferNoSchedule)
                                                .build()
                                ))
                        ),
                        List.of(
                                NodeScore.builder()
                                        .setName("nodeA")
                                        .setScore(ScorePlugin.maxNodeScore)
                                        .build(),
                                NodeScore.builder()
                                        .setName("nodeB")
                                        .setScore(ScorePlugin.maxNodeScore)
                                        .build(),
                                NodeScore.builder()
                                        .setName("nodeC")
                                        .setScore(ScorePlugin.maxNodeScore)
                                        .build()
                        )
                ),
                // the count of taints on a node that are not tolerated by pod, matters.
                Arguments.of(
                        "the more intolerable taints a node has, the lower score it gets.",
                        PodWithTolerations("pod1", List.of(
                                Toleration.builder()
                                        .setKey("foo")
                                        .setOperator(TolerationOperator.Equal)
                                        .setValue("bar")
                                        .setEffect(TaintEffect.PreferNoSchedule)
                                        .build()
                        )),
                        List.of(
                                nodeWithTaints("nodeA",
                                        Collections.emptyList()),
                                nodeWithTaints("nodeB", List.of(
                                        Taint.builder()
                                                .setKey("cpu-type")
                                                .setValue("arm64")
                                                .setEffect(TaintEffect.PreferNoSchedule)
                                                .build()
                                )),
                                nodeWithTaints("nodeC", List.of(
                                        Taint.builder()
                                                .setKey("cpu-type")
                                                .setValue("arm64")
                                                .setEffect(TaintEffect.PreferNoSchedule)
                                                .build(),
                                        Taint.builder()
                                                .setKey("disk-type")
                                                .setValue("ssd")
                                                .setEffect(TaintEffect.PreferNoSchedule)
                                                .build()
                                ))
                        ),
                        List.of(
                                NodeScore.builder()
                                        .setName("nodeA")
                                        .setScore(ScorePlugin.maxNodeScore)
                                        .build(),
                                NodeScore.builder()
                                        .setName("nodeB")
                                        .setScore(ScorePlugin.maxNodeScore / 2)
                                        .build(),
                                NodeScore.builder()
                                        .setName("nodeC")
                                        .setScore(0)
                                        .build()
                        )
                ),
                // taints-tolerations priority only takes care about the taints and tolerations that have effect PreferNoSchedule
                Arguments.of(
                        "only taints and tolerations that have effect PreferNoSchedule are checked by taints-tolerations priority function",
                        PodWithTolerations("pod1", List.of(
                                Toleration.builder()
                                        .setKey("cpu-type")
                                        .setOperator(TolerationOperator.Equal)
                                        .setValue("arm64")
                                        .setEffect(TaintEffect.NoSchedule)
                                        .build(),
                                Toleration.builder()
                                        .setKey("disk-type")
                                        .setOperator(TolerationOperator.Equal)
                                        .setValue("ssd")
                                        .setEffect(TaintEffect.NoSchedule)
                                        .build()
                        )),
                        List.of(
                                nodeWithTaints("nodeA",
                                        Collections.emptyList()),
                                nodeWithTaints("nodeB", List.of(
                                        Taint.builder()
                                                .setKey("cpu-type")
                                                .setValue("arm64")
                                                .setEffect(TaintEffect.NoSchedule)
                                                .build()
                                )),
                                nodeWithTaints("nodeC", List.of(
                                        Taint.builder()
                                                .setKey("cpu-type")
                                                .setValue("arm64")
                                                .setEffect(TaintEffect.PreferNoSchedule)
                                                .build(),
                                        Taint.builder()
                                                .setKey("disk-type")
                                                .setValue("ssd")
                                                .setEffect(TaintEffect.PreferNoSchedule)
                                                .build()
                                ))
                        ),
                        List.of(
                                NodeScore.builder()
                                        .setName("nodeA")
                                        .setScore(ScorePlugin.maxNodeScore)
                                        .build(),
                                NodeScore.builder()
                                        .setName("nodeB")
                                        .setScore(ScorePlugin.maxNodeScore)
                                        .build(),
                                NodeScore.builder()
                                        .setName("nodeC")
                                        .setScore(0)
                                        .build()
                        )
                ),
                Arguments.of(
                        "Default behaviour No taints and tolerations, lands on node with no taints",
                        PodWithTolerations("pod1",
                                Collections.emptyList()),
                        List.of(
                                nodeWithTaints("nodeA",
                                        Collections.emptyList()),
                                nodeWithTaints("nodeB", List.of(
                                        Taint.builder()
                                                .setKey("cpu-type")
                                                .setValue("arm64")
                                                .setEffect(TaintEffect.PreferNoSchedule)
                                                .build()
                                ))
                        ),
                        List.of(
                                NodeScore.builder()
                                        .setName("nodeA")
                                        .setScore(ScorePlugin.maxNodeScore)
                                        .build(),
                                NodeScore.builder()
                                        .setName("nodeB")
                                        .setScore(0)
                                        .build()
                        )
                )
        );
    }
}