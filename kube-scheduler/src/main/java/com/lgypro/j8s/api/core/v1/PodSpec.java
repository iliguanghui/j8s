package com.lgypro.j8s.api.core.v1;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class PodSpec {
    /*
    The priority value. Various system components use this field to find the
	priority of the pod. When Priority Admission Controller is enabled, it
	prevents users from setting this field. The admission controller populates
	this field from PriorityClassName.
	The higher the value, the higher the priority.
     */
    @Setter
    Integer priority;

    List<Container> containers;
    String nodeName;

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        PodSpec podSpec = new PodSpec();

        public PodSpec build() {
            return podSpec;
        }

        public Builder setContainers(List<Container> containers) {
            podSpec.setContainers(containers);
            return this;
        }
    }
}
