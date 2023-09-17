package com.lgypro.j8s.api.core.v1;

import lombok.Getter;
import lombok.Setter;

/**
 * Affinity is a group of affinity scheduling rules.
 */
@Getter
@Setter
public class Affinity {

    NodeAffinity nodeAffinity;

    PodAffinity podAffinity;

    PodAntiAffinity podAntiAffinity;
}
