package com.lgypro.j8s.framework;

import com.lgypro.j8s.api.core.v1.Node;
import com.lgypro.j8s.api.core.v1.Pod;
import com.lgypro.j8s.context.Context;

import java.util.List;

/**
 * PreScorePlugin is an interface for "PreScore" plugin. PreScore is an
 * informational extension point. Plugins will be called with a list of nodes
 * that passed the filtering phase. A plugin may use this data to update internal
 * state or to generate logs/metrics.
 */
public interface PreScorePlugin extends Plugin {
    /**
     * PreScore is called by the scheduling framework after a list of nodes
     * passed the filtering phase. All prescore plugins must return success or
     * the pod will be rejected
     * When it returns Skip status, other fields in status are just ignored,
     * and coupled Score plugin will be skipped in this scheduling cycle.
     */
    Status preScore(Context context, CycleState state, Pod pod, List<Node> nodes);
}
