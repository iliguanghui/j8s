package com.lgypro.j8s.scheduler.testing;

import com.lgypro.j8s.api.core.v1.Pod;

public class PodWrapper {
    Pod pod;

    public PodWrapper(Pod pod) {
        this.pod = pod;
    }

    public PodWrapper setPriority(int priority) {
        pod.getSpec().setPriority(priority);
        return this;
    }

    public Pod getPod() {
        return pod;
    }
}
