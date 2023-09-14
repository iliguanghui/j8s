package com.lgypro.j8s.scheduler.testing;

import com.lgypro.j8s.api.core.v1.Pod;

public class Wrappers {
    public static PodWrapper makePod() {
        return new PodWrapper(new Pod());
    }
}
