package com.lgypro.j8s.api.core.v1;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Container {
    String name;
    String image;
    String[] command;
    String[] args;
    String workingDir;

    ContainerPort[] ports;

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        Container container = new Container();

        public Container build() {
            return container;
        }

        public Builder setName(String name) {
            container.setName(name);
            return this;
        }

        public Builder setImage(String image) {
            container.setImage(image);
            return this;
        }
    }
}
