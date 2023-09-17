package com.lgypro.j8s.framework;

import com.lgypro.j8s.api.core.v1.Node;
import com.lgypro.j8s.api.core.v1.Pod;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Map;

@Getter
@Setter
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

    public void addPodInfo(PodInfo podInfo) {
        pods.add(podInfo);
        if (podInfo.getPod().hasAffinity()) {
            podsWithAffinity.add(podInfo);
        }
        if (podInfo.getPod().hasRequiredAntiAffinity()) {
            podsWithRequiredAntiAffinity.add(podInfo);
        }
        update(podInfo.getPod(), NodeInfoUpdateAction.ADD);
    }

    public void update(Pod pod, NodeInfoUpdateAction action) {

    }

    enum NodeInfoUpdateAction {
        ADD,
        REMOVE
    }
}
