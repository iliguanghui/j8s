package com.lgypro.j8s.framework;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.StringJoiner;

/**
 * Status indicates the result of running a plugin. It consists of a code, a
 * message, (optionally) an error, and a plugin name it fails by.
 * When the status code is not Success, the reasons should explain why.
 * And, when code is Success, all the other fields should be empty.
 * NOTE: A nil Status is also considered as Success.
 */
@Getter
@Setter
public class Status {
    Code code;
    List<String> reasons = new ArrayList<>();

    Exception error;

    String failedPlugin;


    public Code code() {
        return code;
    }

    public String message() {
        StringJoiner joiner = new StringJoiner(", ");
        for (String reason : reasons) {
            joiner.add(reason);
        }
        return joiner.toString();
    }


    public String failedPlugin() {
        return failedPlugin;
    }

    public static Builder builder() {
        return new Builder();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Status anotherStatus) {
            return code == anotherStatus.code;
        }
        return false;
    }

    public static class Builder {
        Status status = new Status();

        public Status build() {
            return status;
        }

        public Builder withError(Exception error) {
            status.setError(error);
            return this;
        }

        public Builder withFailedPlugin(String failedPlugin) {
            status.setFailedPlugin(failedPlugin);
            return this;
        }

        public Builder withReason(String reason) {
            status.getReasons().add(reason);
            return this;
        }

        public Builder withCode(Code code) {
            status.setCode(code);
            return this;
        }
    }
}
