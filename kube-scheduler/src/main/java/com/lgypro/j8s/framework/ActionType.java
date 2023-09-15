package com.lgypro.j8s.framework;

/**
 * ActionType is an integer to represent one type of resource change.
 * Different ActionTypes can be bit-wised to compose new semantics.
 */
public enum ActionType {
    Add(1),
    Delete(1 << 1),
    UpdateNodeAllocatable(1 << 2),
    UpdateNodeLabel(1 << 3),
    UpdateNodeTaint(1 << 4),
    UpdateNodeCondition(1 << 5),
    All((1 << 6) - 1),
    Update(UpdateNodeAllocatable.value | UpdateNodeLabel.value | UpdateNodeTaint.value | UpdateNodeCondition.value),
    UnKnown(-1);

    final int value;

    ActionType(int value) {
        this.value = value;
    }

    public static ActionType fromValue(int value) {
        for (ActionType actionType : values()) {
            if (actionType.value == value) {
                return actionType;
            }
        }
        return UnKnown;
    }

    public int value() {
        return value;
    }
}
