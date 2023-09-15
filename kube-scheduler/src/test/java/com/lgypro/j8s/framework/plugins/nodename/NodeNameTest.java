package com.lgypro.j8s.framework.plugins.nodename;

import com.lgypro.j8s.api.core.v1.Node;
import com.lgypro.j8s.api.core.v1.Pod;
import com.lgypro.j8s.framework.Code;
import com.lgypro.j8s.framework.NodeInfo;
import com.lgypro.j8s.framework.Status;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

class NodeNameTest {
    NodeName nodeName = NodeName.newInstance(null, null);

    @ParameterizedTest
    @MethodSource
    void testNodeName(Pod pod, Node node, String description, Status expected) {
        NodeInfo nodeInfo = NodeInfo.builder().setNode(node).build();
        Status actual = nodeName.filter(null, null, pod, nodeInfo);
        Assertions.assertEquals(expected, actual);
    }

    public static Stream<Arguments> testNodeName() {
        return Stream.of(
                Arguments.of(
                        /* target pod */
                        Pod.builder().build(),
                        /* target node */
                        Node.builder().build(),
                        /* description  */
                        "no host specified",
                        Status.builder().withCode(Code.SUCCESS).build()
                ),
                Arguments.of(
                        Pod.builder().setNodeName("foo").build(),
                        Node.builder().setName("foo").build(),
                        "host matches",
                        Status.builder().withCode(Code.SUCCESS).build()
                ),
                Arguments.of(
                        Pod.builder().setNodeName("bar").build(),
                        Node.builder().setName("foo").build(),
                        "host doesn't match",
                        Status.builder().withCode(Code.UNSCHEDULABLEANDUNRESOLVABLE).withReason(com.lgypro.j8s.framework.plugins.nodename.NodeName.errorReason).build()
                )
        );
    }
}