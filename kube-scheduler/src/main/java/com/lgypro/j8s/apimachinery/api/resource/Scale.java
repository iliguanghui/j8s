package com.lgypro.j8s.apimachinery.api.resource;

public enum Scale {
    Nano(-9),
    Micro(-6),
    Milli(-3),
    kilo(3),
    Mega(6),
    Giga(9),
    Tera(12),
    Peta(15),
    Exa(18);


    final int value;

    Scale(int value) {
        this.value = value;
    }
}
