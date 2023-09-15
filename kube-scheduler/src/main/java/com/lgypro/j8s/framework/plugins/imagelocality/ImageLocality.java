package com.lgypro.j8s.framework.plugins.imagelocality;

import com.lgypro.j8s.api.core.v1.Container;
import com.lgypro.j8s.api.core.v1.Pod;
import com.lgypro.j8s.context.Context;
import com.lgypro.j8s.framework.*;
import com.lgypro.j8s.framework.plugins.names.Names;

import java.util.List;

public class ImageLocality implements ScorePlugin {
    static final long mb = 1024 * 1024;
    static final long minThreshold = 23 * mb;
    static final long maxContainerThreshold = 1000 * mb;

    public static final String name = Names.ImageLocality;

    private final Handle handle;

    public ImageLocality(Handle handle) {
        this.handle = handle;
    }

    @Override
    public String name() {
        return name;
    }

    public static ImageLocality newInstance(Object object, Handle handle) {
        return new ImageLocality(handle);
    }

    @Override
    public Object[] score(Context context, CycleState state, Pod pod, String nodeName) {
        NodeInfo nodeInfo = handle.snapshotSharedLister().nodeInfos().get(nodeName);
        List<NodeInfo> nodeInfos = handle.snapshotSharedLister().nodeInfos().list();
        int totalNumNodes = nodeInfos.size();
        List<Container> containers = pod.getSpec().getContainers();
        long score = calculatePriority(sumImageScores(nodeInfo, containers, totalNumNodes), containers.size());
        return new Object[]{score, null};
    }

    @Override
    public ScoreExtensions scoreExtensions() {
        return null;
    }

    static long calculatePriority(long sumScores, int numContainers) {
        long maxThreshold = maxContainerThreshold * numContainers;
        if (sumScores < minThreshold) {
            sumScores = minThreshold;
        } else if (sumScores > maxThreshold) {
            sumScores = maxThreshold;
        }
        return (sumScores - minThreshold) / (maxThreshold - minThreshold) * ScorePlugin.maxNodeScore;
    }

    static long sumImageScores(NodeInfo nodeInfo, List<Container> containers, int totalNumNodes) {
        long sum = 0;
        for (Container container : containers) {
            ImageStateSummary state = nodeInfo.getImageStates().get(normalizeImageName(container.getImage()));
            if (state != null) {
                sum += scaledImageScore(state, totalNumNodes);
            }
        }
        return sum;
    }

    static long scaledImageScore(ImageStateSummary imageStateSummary, int totalNumNodes) {
        double spread = 1.0 * imageStateSummary.getNumNodes() / totalNumNodes;
        return (long) (imageStateSummary.getSize() * spread);
    }

    static String normalizeImageName(String imageName) {
        if (imageName.lastIndexOf(":") <= imageName.lastIndexOf("/")) {
            imageName += ":latest";
        }
        return imageName;
    }
}
