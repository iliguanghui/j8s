package com.lgypro.j8s.api.core.v1;

/**
 * A toleration operator is the set of operators that can be used in a toleration
 */
public enum TolerationOperator {
    Exists("Exists"),
    Equal("Equal");

    final String value;

    TolerationOperator(String value) {
        this.value = value;
    }
}