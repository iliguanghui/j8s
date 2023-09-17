package com.lgypro.j8s.api.core.v1;

public enum ResourceName {
    ResourceCPU("cpu"),
    ResourceMemory("memory"),
    ResourceStorage("storage"),
    ResourceEphemeralStorage("ephemeral-storage"),
    ResourcePods("pods"),
    // Services, number
    ResourceServices("services"),
    // ReplicationControllers, number
    ResourceReplicationControllers("replicationcontrollers"),
    // ResourceQuotas, number
    ResourceQuotas("resourcequotas"),
    // ResourceSecrets, number
    ResourceSecrets("secrets"),
    // ResourceConfigMaps, number
    ResourceConfigMaps("configmaps"),
    // ResourcePersistentVolumeClaims, number
    ResourcePersistentVolumeClaims("persistentvolumeclaims"),
    // ResourceServicesNodePorts, number
    ResourceServicesNodePorts("services.nodeports"),
    // ResourceServicesLoadBalancers, number
    ResourceServicesLoadBalancers("services.loadbalancers"),
    // CPU request, in cores. (500m = .5 cores)
    ResourceRequestsCPU("requests.cpu"),
    // Memory request, in bytes. (500Gi = 500GiB = 500 * 1024 * 1024 * 1024)
    ResourceRequestsMemory("requests.memory"),
    // Storage request, in bytes
    ResourceRequestsStorage("requests.storage"),
    // Local ephemeral storage request, in bytes. (500Gi = 500GiB = 500 * 1024 * 1024 * 1024)
    ResourceRequestsEphemeralStorage("requests.ephemeral-storage"),
    // CPU limit, in cores. (500m = .5 cores)
    ResourceLimitsCPU("limits.cpu"),
    // Memory limit, in bytes. (500Gi = 500GiB = 500 * 1024 * 1024 * 1024)
    ResourceLimitsMemory("limits.memory"),
    // Local ephemeral storage limit, in bytes. (500Gi = 500GiB = 500 * 1024 * 1024 * 1024)
    ResourceLimitsEphemeralStorage("limits.ephemeral-storage");

    final String value;

    ResourceName(String value) {
        this.value = value;
    }
}
