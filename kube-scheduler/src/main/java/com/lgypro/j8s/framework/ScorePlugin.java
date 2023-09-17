package com.lgypro.j8s.framework;

import com.lgypro.j8s.api.core.v1.Pod;
import com.lgypro.j8s.context.Context;

/**
 * ScorePlugin is an interface that must be implemented by "Score" plugins to rank
 * nodes that passed the filtering phase.
 */
public interface ScorePlugin extends Plugin {
    int maxNodeScore = 100;
    int minNodeScore = 0;
    int maxTotalScore = Integer.MAX_VALUE;

    /**
     * Score is called on each filtered node. It must return success and an integer
     * indicating the rank of the node. All scoring plugins must return success or
     * the pod will be rejected.
     */
    Object[] score(Context context, CycleState state, Pod pod, String nodeName);

    /**
     * ScoreExtensions returns a ScoreExtensions interface if it implements one, or nil if does not.
     */
    ScoreExtensions scoreExtensions();
}
