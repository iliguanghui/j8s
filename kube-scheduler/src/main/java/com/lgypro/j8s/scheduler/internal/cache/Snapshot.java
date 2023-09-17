package com.lgypro.j8s.scheduler.internal.cache;

import com.lgypro.j8s.api.core.v1.Node;
import com.lgypro.j8s.api.core.v1.Pod;
import com.lgypro.j8s.framework.NodeInfo;
import org.apache.logging.log4j.util.Strings;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Snapshot {
    public static Snapshot newSnapshot(List<Pod> pods, List<Node> nodes) {
        return new Snapshot();
    }

    /**
     * createNodeInfoMap obtains a list of pods and pivots that list into a map
     * where the keys are node names and the values are the aggregated information
     * for that node.
     */
    public static Map<String, NodeInfo> createNodeInfoMap(List<Pod> pods, List<Node> nodes) {
        HashMap<String, NodeInfo> nodeNameToInfo = new HashMap<>();
        if (pods != null && !pods.isEmpty()) {
            for (Pod pod : pods) {
                String nodeName = pod.getSpec().getNodeName();
                if (Strings.isNotEmpty(nodeName)) {

                }
            }
        }
        return nodeNameToInfo;
    }
}
