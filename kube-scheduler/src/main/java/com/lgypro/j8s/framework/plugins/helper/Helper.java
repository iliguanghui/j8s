package com.lgypro.j8s.framework.plugins.helper;


import com.lgypro.j8s.framework.Code;
import com.lgypro.j8s.framework.NodeScore;
import com.lgypro.j8s.framework.Status;

import java.util.List;

public class Helper {
    /**
     * DefaultNormalizeScore generates a Normalize Score function that can normalize the
     * scores from [0, max(scores)] to [0, maxPriority]. If reverse is set to true, it
     * reverses the scores by subtracting it from maxPriority.
     * Note: The input scores are always assumed to be non-negative integers.
     */
    public static Status defaultNormalizeScore(int maxPriority, boolean reverse, List<NodeScore> scores) {
        int maxScore = 0;
        for (NodeScore nodeScore : scores) {
            if (nodeScore.getScore() > maxScore) {
                maxScore = nodeScore.getScore();
            }
        }
        if (maxScore == 0) {
            if (reverse) {
                for (NodeScore nodeScore : scores) {
                    nodeScore.setScore(maxPriority);
                }
            }
        }
        for (NodeScore nodeScore : scores) {
            int priority = nodeScore.getScore() / maxScore * maxPriority;
            if (reverse) {
                priority = maxPriority - priority;
            }
            nodeScore.setScore(priority);
        }
        return Status.builder().withCode(Code.SUCCESS).build();
    }
}
