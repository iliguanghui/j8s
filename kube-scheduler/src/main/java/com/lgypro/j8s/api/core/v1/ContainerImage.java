package com.lgypro.j8s.api.core.v1;

import java.util.List;

public class ContainerImage {

    /*
    names by which this image is known
     */
    List<String> names;
    /*
    The size of the image in bytes
     */
    long sizeBytes;

    public ContainerImage(List<String> names, long sizeBytes) {
        this.names = names;
        this.sizeBytes = sizeBytes;
    }
}