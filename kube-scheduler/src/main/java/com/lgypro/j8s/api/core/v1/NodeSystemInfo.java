package com.lgypro.j8s.api.core.v1;

/*
NodeSystemInfo is a set of ids/uuids to uniquely identify the node.
 */
public class NodeSystemInfo {
    String machineID;

    String systemUUID;

    String bootID;

    String kernelVersion;
    String osImage;

    String containerRuntimeVersion;

    String kubeletVersion;

    String kubeProxyVersion;

    String operationSystem;

    String architecture;
}