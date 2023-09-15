package com.lgypro.j8s.framework;

import java.util.List;

public interface EnqueueExtensions {
    /**
     * EventsToRegister returns a series of possible events that may cause a Pod
     * failed by this plugin schedulable.
     * The events will be registered when instantiating the internal scheduling queue,
     * and leveraged to build event handlers dynamically.
     * Note: the returned list needs to be static (not depend on configuration parameters);
     * otherwise it would lead to undefined behavior
     */
    List<ClusterEvent> eventsToRegister();
}
