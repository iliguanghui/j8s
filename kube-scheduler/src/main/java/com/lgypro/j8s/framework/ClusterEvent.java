package com.lgypro.j8s.framework;

import lombok.Getter;
import lombok.Setter;

/**
 * abstracts how a system resource's state gets changed.
 * Resource represents the standard API resources such as Pod, Node, etc.
 * ActionType denotes the specific change such as Add, Update or Delete.
 */
@Getter
@Setter
public class ClusterEvent {
    GroupVersionKind resource;
    ActionType actionType;
    String label;

    public ClusterEvent(GroupVersionKind resource, ActionType actionType, String label) {
        this.resource = resource;
        this.actionType = actionType;
        this.label = label;
    }
}
