package com.lgypro.j8s.api.core.v1;

import lombok.Getter;
import lombok.Setter;
import org.apache.logging.log4j.util.Strings;

/**
 * The pod this Toleration is attached to tolerates any taint that matches
 * the triple <key,value,effect> using the matching operator <operator>.
 */
@Getter
@Setter
public class Toleration {
    /**
     * Key is the taint key that the toleration applies to. Empty means match all taint keys.
     * If the key is empty, operator must be Exists; this combination means to match all values and all keys.
     * +optional
     */
    String key;
    /**
     * Operator represents a key's relationship to the value.
     * Valid operators are Exists and Equal.
     * Defaults to Equal.
     * Exists is equivalent to wildcard for value, so that a pod can tolerate all taints of a particular category.
     * +optional
     */
    TolerationOperator operator;

    /**
     * Value is the taint value the toleration matches to.
     * If the operator is Exists, the value should be empty, otherwise just a regular string.
     * +optional
     */
    String value;
    /**
     * Effect indicates the taint effect to match. Empty means match all taint effects.
     * When specified, allowed values are NoSchedule, PreferNoSchedule and NoExecute.
     * +optional
     */
    TaintEffect effect;
    /**
     * TolerationSeconds represents the period of time the toleration (which must be
     * of effect NoExecute, otherwise this field is ignored) tolerates the taint. By default,
     * it is not set, which means tolerate the taint forever (do not evict). Zero and
     * negative values will be treated as 0 (evict immediately) by the system.
     * +optional
     */
    long tolerationSeconds;

    public static Builder builder() {
        return new Builder();
    }
   public static class Builder {
        Toleration toleration = new Toleration();

        public Toleration build() {
            return toleration;
        }

        public Builder setKey(String key) {
            toleration.setKey(key);
            return this;
        }

        public Builder setOperator(TolerationOperator operator) {
            toleration.setOperator(operator);
            return this;
        }

        public Builder setValue(String value) {
            toleration.setValue(value);
            return this;
        }

        public Builder setEffect(TaintEffect effect) {
            toleration.setEffect(effect);
            return this;
        }

        public Builder setTolerationSeconds(long tolerationSeconds) {
            toleration.setTolerationSeconds(tolerationSeconds);
            return this;
        }

    }

    /**
     * ToleratesTaint checks if the toleration tolerates the taint.
     * The matching follows the rules below:
     * 1. Empty toleration.effect means to match all taint effects,
     * otherwise taint effect must equal to toleration.effect.
     * 2. If toleration.operator is 'Exists', it means to match all taint values.
     * 3. Empty toleration.key means to match all taint keys.
     * If toleration.key is empty, toleration.operator must be 'Exists';
     * this combination means to match all taint values and all taint keys.
     */
    public boolean toleratesTaint(Taint taint) {
        if (effect != null && !effect.equals(taint.getEffect())) {
            return false;
        }
        if (Strings.isNotEmpty(key) && !key.equals(taint.getKey())) {
            return false;
        }
        return switch (operator) {
            case Exists -> true;
            case Equal -> Strings.isNotEmpty(value) && value.equals(taint.getValue());
        };
    }
}
