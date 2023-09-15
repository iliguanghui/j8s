package com.lgypro.j8s.api.core.v1;

/**
 * Protocol defines network protocols supported for things like container ports
 */
public enum Protocol {
    TCP("TCP"),
    UDP("UDP"),
    SCTP("SCTP");

    final String name;

    Protocol(String name) {
        this.name = name;
    }
}
