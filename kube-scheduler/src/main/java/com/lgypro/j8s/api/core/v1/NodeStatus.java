package com.lgypro.j8s.api.core.v1;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

/*
NodeStatus is information about the current status of a node.
 */
@Getter
@Setter
public class NodeStatus {
    /*
    Capacity represents the total resources of a node.
     */
    ResourceList capacity;

    /*
    Allocatable represents the resources of a node that are available for scheduling.
     */
    ResourceList allocatable;

    List<NodeCondition> conditions;

    List<NodeAddress> addresses;

    NodeDaemonEndpoints daemonEndpoints;

    /*
    Set of ids/uuids to uniquely identify the node.
     */
    NodeSystemInfo nodeInfo;

    List<ContainerImage> images;

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        NodeStatus nodeStatus = new NodeStatus();

        public NodeStatus build() {
            return nodeStatus;
        }

        public Builder setImages(List<ContainerImage> images) {
            nodeStatus.setImages(images);
            return this;
        }
    }
}
