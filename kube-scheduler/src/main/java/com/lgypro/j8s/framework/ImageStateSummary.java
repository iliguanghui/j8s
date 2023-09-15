package com.lgypro.j8s.framework;

import lombok.Getter;

@Getter
public class ImageStateSummary {
    /* size of the image in bytes */
    long size;
    /* used to track how many nodes have this image */
    int numNodes;
}
