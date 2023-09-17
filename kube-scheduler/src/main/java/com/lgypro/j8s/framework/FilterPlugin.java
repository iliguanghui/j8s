package com.lgypro.j8s.framework;

import com.lgypro.j8s.api.core.v1.Pod;
import com.lgypro.j8s.context.Context;

/**
 * FilterPlugin is an interface for Filter plugins. These plugins are called at the
 * filter extension point for filtering out hosts that cannot run a pod.
 * This concept used to be called 'predicate' in the original scheduler.
 * These plugins should return "Success", "Unschedulable" or "Error" in Status.code.
 * However, the scheduler accepts other valid codes as well.
 * Anything other than "Success" will lead to exclusion of the given host from
 * running the pod.
 */
public interface FilterPlugin extends Plugin {
    /**
     * Filter is called by the scheduling framework.
     * All FilterPlugins should return "Success" to declare that
     * the given node fits the pod. If Filter doesn't return "Success",
     * it will return "Unschedulable", "UnschedulableAndUnresolvable" or "Error".
     * For the node being evaluated, Filter plugins should look at the passed
     * nodeInfo reference for this particular node's information (e.g., pods
     * considered to be running on the node) instead of looking it up in the
     * NodeInfoSnapshot because we don't guarantee that they will be the same.
     * For example, during preemption, we may pass a copy of the original
     * nodeInfo object that has some pods removed from it to evaluate the
     * possibility of preempting them to schedule the target pod.
     */

    Status filter(Context context, CycleState state, Pod pod, NodeInfo nodeInfo);
}
