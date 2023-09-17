package com.lgypro.j8s.componenthelpers.scheduling.corev1;

import com.lgypro.j8s.api.core.v1.Pod;
import com.lgypro.j8s.api.core.v1.Taint;
import com.lgypro.j8s.api.core.v1.Toleration;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

public final class Helpers {
    private Helpers() {
    }

    public static int getPodPriority(Pod pod) {
        Integer priority = pod.getSpec().getPriority();
        if (priority != null) {
            return priority;
        }
        return 0;
    }

    /**
     * FindMatchingUntoleratedTaint checks if the given tolerations tolerates
     * all the filtered taints, and returns the first taint without a toleration
     */
    public static Taint findMatchingUntoleratedTaint(List<Taint> taints, List<Toleration> tolerations, Predicate<Taint> inclusionFilter) {
        List<Taint> filteredTaints = getFilteredTaints(taints, inclusionFilter);
        for (Taint taint : filteredTaints) {
            if (!tolerationsTolerateTaint(tolerations, taint)) {
                return taint;
            }
        }
        return null;
    }

    /**
     * getFilteredTaints returns a list of taints satisfying the filter predicate
     */
    static List<Taint> getFilteredTaints(List<Taint> taints, Predicate<Taint> inclusionFilter) {
        if (inclusionFilter == null) {
            return taints;
        }
        List<Taint> filteredTaints = new ArrayList<>();
        for (Taint taint : taints) {
            if (inclusionFilter.test(taint)) {
                filteredTaints.add(taint);
            }
        }
        return filteredTaints;
    }

    /**
     * TolerationsTolerateTaint checks if taint is tolerated by any of the tolerations.
     */
    public static boolean tolerationsTolerateTaint(List<Toleration> tolerations, Taint taint) {
        for (Toleration toleration : tolerations) {
            if (toleration.toleratesTaint(taint)) {
                return true;
            }
        }
        return false;
    }
}
