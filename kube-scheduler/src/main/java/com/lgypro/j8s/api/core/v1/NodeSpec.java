package com.lgypro.j8s.api.core.v1;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class NodeSpec {
    String podCIDR;

    List<String> podCIDRs;

    List<Taint> taints;

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        NodeSpec nodeSpec = new NodeSpec();

        public NodeSpec build() {
            return nodeSpec;
        }

        public Builder setPodCIDR(String podCIDR) {
            nodeSpec.setPodCIDR(podCIDR);
            return this;
        }

        public Builder setPodCIDRs(List<String> podCIDRs) {
            nodeSpec.setPodCIDRs(podCIDRs);
            return this;
        }

        public Builder setTaints(List<Taint> taints) {
            nodeSpec.setTaints(taints);
            return this;
        }
    }
}
