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

    Status filter(Context context, CycleState state, Pod pod, NodeInfo nodeInfo);
}
