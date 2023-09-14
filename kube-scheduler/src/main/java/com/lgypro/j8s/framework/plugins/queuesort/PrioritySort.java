package com.lgypro.j8s.framework.plugins.queuesort;

import com.lgypro.j8s.componenthelpers.scheduling.corev1.Helpers;
import com.lgypro.j8s.framework.QueueSortPlugin;
import com.lgypro.j8s.framework.QueuedPodInfo;
import com.lgypro.j8s.framework.plugins.names.Names;

public class PrioritySort implements QueueSortPlugin {
    public static String name = Names.PrioritySort;

    public boolean less(QueuedPodInfo podInfo1, QueuedPodInfo podInfo2) {
        int priority1 = Helpers.getPodPriority(podInfo1.getPodInfo().getPod());
        int priority2 = Helpers.getPodPriority(podInfo2.getPodInfo().getPod());
        return (priority1 > priority2)
                || (priority1 == priority2 &&
                podInfo1.getTimestamp().isBefore(podInfo2.getTimestamp()));
    }

    @Override
    public String name() {
        return name;
    }

    public static PrioritySort newInstance() {
        return new PrioritySort();
    }
}
