package com.lgypro.j8s.api.core.v1;

/**
 * The weights of all of the matched WeightedPodAffinityTerm fields are added per-node to find the most preferred node(s)
 */
public class WeightedPodAffinityTerm {
    PodAffinityTerm podAffinityTerm;

    int weight;
}
