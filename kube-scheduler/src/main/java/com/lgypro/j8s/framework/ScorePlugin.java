package com.lgypro.j8s.framework;

import com.lgypro.j8s.api.core.v1.Pod;
import com.lgypro.j8s.context.Context;

/**
 * ScorePlugin is an interface that must be implemented by "Score" plugins to rank
 * nodes that passed the filtering phase.
 */
public interface ScorePlugin extends Plugin {
    long maxNodeScore = 100;
    long minNodeScore = 0;
    long maxTotalScore = Long.MAX_VALUE;

    Object[] score(Context context, CycleState state, Pod pod, String nodeName);

    ScoreExtensions scoreExtensions();
}
