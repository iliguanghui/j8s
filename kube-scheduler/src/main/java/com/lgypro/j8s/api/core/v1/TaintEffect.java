package com.lgypro.j8s.api.core.v1;

public enum TaintEffect {

    /**
     * Do not allow new pods to schedule onto the node unless they tolerate the taint,
     * but allow all pods submitted to Kubelet without going through the scheduler
     * to start, and allow all already-running pods to continue running.
     * Enforced by the scheduler.
     */
    NoSchedule("NoSchedule"),
    /**
     * Like TaintEffectNoSchedule, but the scheduler tries not to schedule
     * new pods onto the node, rather than prohibiting new pods from scheduling
     * onto the node entirely. Enforced by the scheduler.
     */
    PreferNoSchedule("PreferNoSchedule"),
    /**
     * Evict any already-running pods that do not tolerate the taint.
     * Currently enforced by NodeController.
     */
    NoExecute("NoExecute");

    final String value;

    TaintEffect(String value) {
        this.value = value;
    }
}
