package com.lgypro.j8s.api.core.v1;

import com.lgypro.j8s.apis.meta.v1.ObjectMeta;
import com.lgypro.j8s.apis.meta.v1.TypeMeta;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Pod {
    TypeMeta typeMeta;
    ObjectMeta objectMeta;
    @Getter
    @Setter
    PodSpec spec;
    @Getter
    @Setter
    PodStatus status;

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        Pod pod = new Pod();

        public Pod build() {
            return pod;
        }

        public Builder setSpec(PodSpec spec) {
            pod.setSpec(spec);
            return this;
        }

        public Builder setNodeName(String nodeName) {
            pod.getSpec().setNodeName(nodeName);
            return this;
        }

        public Builder setObjectMeta(ObjectMeta objectMeta) {
            pod.objectMeta = objectMeta;
            return this;
        }
    }

    public Pod() {
        spec = new PodSpec();
    }

    public boolean hasAffinity() {
        Affinity affinity = spec.getAffinity();
        return affinity != null && (affinity.getPodAffinity() != null || affinity.getPodAntiAffinity() != null);
    }

    public boolean hasRequiredAntiAffinity() {
        Affinity affinity = spec.getAffinity();
        return affinity != null && affinity.getPodAntiAffinity() != null && affinity.getPodAntiAffinity().getRequiredDuringSchedulingIgnoredDuringExecution() != null;
    }
}
