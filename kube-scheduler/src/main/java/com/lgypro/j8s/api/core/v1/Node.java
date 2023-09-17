package com.lgypro.j8s.api.core.v1;

import com.lgypro.j8s.apis.meta.v1.ObjectMeta;
import com.lgypro.j8s.apis.meta.v1.TypeMeta;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Node {
    TypeMeta typeMeta;

    ObjectMeta objectMeta;

    NodeSpec spec;

    NodeStatus status;

    public Node() {
        typeMeta = new TypeMeta();
        objectMeta = new ObjectMeta();
        spec = new NodeSpec();
        status = new NodeStatus();
    }

    public Node(String name) {
        this();
        objectMeta.setName(name);
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        Node node = new Node();

        public Node build() {
            return node;
        }

        public Builder setName(String name) {
            node.objectMeta.setName(name);
            return this;
        }

        public Builder setObjectMeta(ObjectMeta objectMeta) {
            node.objectMeta = objectMeta;
            return this;
        }

        public Builder setNodeSpec(NodeSpec spec) {
            node.spec = spec;
            return this;
        }
    }
}
