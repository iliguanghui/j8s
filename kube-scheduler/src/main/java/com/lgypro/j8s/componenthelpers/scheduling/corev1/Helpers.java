package com.lgypro.j8s.componenthelpers.scheduling.corev1;

import com.lgypro.j8s.api.core.v1.Pod;

public final class Helpers {
    private Helpers() {
    }

    public static int getPodPriority(Pod pod) {
        Integer priority = pod.getSpec().getPriority();
        if (priority != null) {
            return priority;
        }
        return 0;
    }
}
