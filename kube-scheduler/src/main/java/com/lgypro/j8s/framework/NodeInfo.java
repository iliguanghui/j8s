package com.lgypro.j8s.framework;

import com.lgypro.j8s.api.core.v1.Node;
import lombok.Getter;

import java.util.List;
import java.util.Map;

@Getter
public class NodeInfo {
    Node node;
    List<PodInfo> pods;

    List<PodInfo> podsWithAffinity;

    List<PodInfo> podsWithRequiredAntiAffinity;

    HostPortInfo usedPorts;
    Resource requested;

    Resource nonZeroRequested;

    Resource allocatable;

    Map<String, ImageStateSummary> imageStates;

    Map<String, Integer> PVCRefCounts;

    long generation;

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        NodeInfo nodeInfo = new NodeInfo();

        public NodeInfo build() {
            return nodeInfo;
        }

        public Builder setNode(Node node) {
            nodeInfo.node = node;
            return this;
        }
    }
}
