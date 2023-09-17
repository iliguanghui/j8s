package com.lgypro.j8s.framework;

import com.lgypro.j8s.api.core.v1.Pod;
import com.lgypro.j8s.context.Context;

import java.util.List;

/**
 * ScoreExtensions is an interface for Score extended functionality
 */
public interface ScoreExtensions {
    /**
     * NormalizeScore is called for all node scores produced by the same plugin's "Score"
     * method. A successful run of NormalizeScore will update the scores list and return
     * a success status.
     */
    Status normalizeScore(Context ctx, CycleState state, Pod pod, List<NodeScore> scores);
}
