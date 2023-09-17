package com.lgypro.j8s.apis.meta.v1;

import lombok.Getter;
import lombok.Setter;

import java.util.Map;

/**
 * ObjectMeta is metadata that all persisted resources must have, which includes all objects
 * users must create.
 */
@Getter
@Setter
public class ObjectMeta {
    /**
     * Name must be unique within a namespace. Is required when creating resources, although
     * some resources may allow a client to request the generation of an appropriate name
     * automatically. Name is primarily intended for creation idempotence and configuration
     * definition.
     * Cannot be updated.
     * More info: <a href="https://kubernetes.io/docs/concepts/overview/working-with-objects/names#names">...</a>
     * +optional
     */
    String name;
    /**
     * Namespace defines the space within which each name must be unique. An empty namespace is
     * equivalent to the "default" namespace, but "default" is the canonical representation.
     * Not all objects are required to be scoped to a namespace - the value of this field for
     * those objects will be empty.
     * <p>
     * Must be a DNS_LABEL.
     * Cannot be updated.
     * More info: <a href="https://kubernetes.io/docs/concepts/overview/working-with-objects/namespaces">...</a>
     * +optional
     */
    String namespace;

    /**
     * Map of string keys and values that can be used to organize and categorize
     * (scope and select) objects. May match selectors of replication controllers
     * and services.
     * More info: <a href="https://kubernetes.io/docs/concepts/overview/working-with-objects/labels">...</a>
     * +optional
     */
    Map<String, String> labels;

    /**
     * Annotations is an unstructured key value map stored with a resource that may be
     * set by external tools to store and retrieve arbitrary metadata. They are not
     * queryable and should be preserved when modifying objects.
     * More info: <a href="https://kubernetes.io/docs/concepts/overview/working-with-objects/annotations">...</a>
     * +optional
     */
    Map<String, String> annotations;

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        ObjectMeta objectMeta = new ObjectMeta();

        public Builder setName(String name) {
            objectMeta.setName(name);
            return this;
        }

        public Builder setNamespace(String namespace) {
            objectMeta.setNamespace(namespace);
            return this;
        }

        public Builder setLabels(Map<String, String> labels) {
            objectMeta.setLabels(labels);
            return this;
        }

        public Builder setAnnotations(Map<String, String> annotations) {
            objectMeta.setAnnotations(annotations);
            return this;
        }

        public ObjectMeta build() {
            return objectMeta;
        }
    }
}
