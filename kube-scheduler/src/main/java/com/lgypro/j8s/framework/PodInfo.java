package com.lgypro.j8s.framework;

import com.lgypro.j8s.api.core.v1.Pod;
import lombok.Getter;

// PodInfo is a wrapper to a Pod with additional pre-computed information to
// accelerate processing. This information is typically immutable (e.g., pre-processed
// inter-pod affinity selectors).
public class PodInfo {
    @Getter
    Pod pod;

    public PodInfo(Pod pod) {
        this.pod = pod;
    }
}
