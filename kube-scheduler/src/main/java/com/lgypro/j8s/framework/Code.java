package com.lgypro.j8s.framework;

/**
 * Code is the Status code/type which is returned from plugins.
 */
public enum Code {
    /**
     * Success means that plugin ran correctly and found pod schedulable.
     * NOTE: A nil status is also considered as "Success".
     */
    SUCCESS(0),
    /**
     * Error is used for internal plugin errors, unexpected input, etc.
     */
    ERROR(1),
    /**
     * Unschedulable is used when a plugin finds a pod unschedulable. The scheduler might attempt to
     * run other postFilter plugins like preemption to get this pod scheduled.
     * Use UnschedulableAndUnresolvable to make the scheduler skipping other postFilter plugins.
     * The accompanying status message should explain why the pod is unschedulable.
     */
    UNSCHEDULABLE(2),
    /**
     * UnschedulableAndUnresolvable is used when a plugin finds a pod unschedulable and
     * other postFilter plugins like preemption would not change anything.
     * Plugins should return Unschedulable if it is possible that the pod can get scheduled
     * after running other postFilter plugins.
     * The accompanying status message should explain why the pod is unschedulable.
     */
    UNSCHEDULABLEANDUNRESOLVABLE(3),
    /**
     * Wait is used when a Permit plugin finds a pod scheduling should wait.
     */
    WAIT(4),
    /**
     * Skip is used in the following scenarios:
     * - when a Bind plugin chooses to skip binding.
     * - when a PreFilter plugin returns Skip so that coupled Filter plugin/PreFilterExtensions() will be skipped.
     * - when a PreScore plugin returns Skip so that coupled Score plugin will be skipped.
     */
    SKIP(5);
    final int value;

    Code(int value) {
        this.value = value;
    }
}
