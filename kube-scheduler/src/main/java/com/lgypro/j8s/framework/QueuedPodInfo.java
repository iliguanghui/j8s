package com.lgypro.j8s.framework;

import lombok.Getter;

import java.time.Instant;

/**
 * QueuedPodInfo is a Pod wrapper with additional information related to
 * the pod's status in the scheduling queue, such as the timestamp when
 * it's added to the queue.
 */
public class QueuedPodInfo {
    @Getter
    PodInfo podInfo;
    // The time pod added to the scheduling queue.
    @Getter
    Instant timestamp;

    public QueuedPodInfo(PodInfo podInfo, Instant timestamp) {
        this.podInfo = podInfo;
        this.timestamp = timestamp;
    }

    public QueuedPodInfo(PodInfo podInfo) {
        this.podInfo = podInfo;
        timestamp = Instant.now();
    }
}
