package com.lgypro.j8s.api.core.v1;

import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

/**
 * The node this Taint is attached to has the "effect" on
 * any pod that does not tolerate the Taint.
 */
@Getter
@Setter
public class Taint {
    /**
     * Required. The taint key to be applied to a node
     */
    String key;
    /**
     * The taint value corresponding to the taint key.
     */
    String value;
    /**
     * Required. The effect of the taint on pods
     * that do not tolerate the taint.
     * Valid effects are NoSchedule, PreferNoSchedule and NoExecute.
     */
    TaintEffect effect;
    /**
     * TimeAdded represents the time at which the taint was added.
     * It is only written for NoExecute taints.
     */
    Instant timeAdded;

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        Taint taint = new Taint();

        public Taint build() {
            return taint;
        }

        public Builder setKey(String key) {
            taint.setKey(key);
            return this;
        }

        public Builder setValue(String value) {
            taint.setValue(value);
            return this;
        }

        public Builder setEffect(TaintEffect effect) {
            taint.setEffect(effect);
            return this;
        }

        public Builder setTimeAdded(Instant timeAdded) {
            taint.setTimeAdded(timeAdded);
            return this;
        }
    }
}
