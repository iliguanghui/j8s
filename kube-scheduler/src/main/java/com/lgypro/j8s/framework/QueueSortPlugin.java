package com.lgypro.j8s.framework;

/**
 * QueueSortPlugin is an interface that must be implemented by "QueueSort" plugins.
 * These plugins are used to sort pods in the scheduling queue. Only one queue sort
 * plugin may be enabled at a time.
 */
public interface QueueSortPlugin extends Plugin {
    boolean less(QueuedPodInfo podInfo1, QueuedPodInfo podInfo2);
}
