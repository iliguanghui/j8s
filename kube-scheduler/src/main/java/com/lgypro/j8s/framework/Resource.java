package com.lgypro.j8s.framework;

import com.lgypro.j8s.api.core.v1.ResourceName;
import com.lgypro.j8s.apimachinery.api.resource.Quantity;

import java.util.Map;

/**
 * Resource is a collection of compute resource.
 */
public class Resource {
    long milliCPU;
    long memory;
    long ephemeralStorage;
    int allowedPodNumber;

    Map<ResourceName, Long> scalarResources;

    public void add(Map<ResourceName, Quantity> resources) {
        resources.forEach((name, quantity) -> {
            switch (name) {
                case ResourceCPU:
                    milliCPU += quantity.milliValue();
                    break;
                case ResourceMemory:
                    memory += quantity.value();
                    break;
                case ResourcePods:
                    allowedPodNumber += (int) quantity.value();
                    break;
                case ResourceEphemeralStorage:
                    ephemeralStorage += quantity.value();
                    break;
                default:
                    scalarResources.put(name, quantity.value());
            }
        });
    }
}
