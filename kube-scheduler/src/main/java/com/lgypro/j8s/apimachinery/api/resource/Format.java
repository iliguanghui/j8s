package com.lgypro.j8s.apimachinery.api.resource;

public enum Format {
    DecimalExponent("DecimalExponent"),
    DecimalSI("DecimalSI"),

    BinarySI("BinarySI");
    final String value;

    Format(String value) {
        this.value = value;
    }
}
