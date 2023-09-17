package com.lgypro.j8s.framework;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NodeScore {
    String name;
    int score;

    public NodeScore(String name, int score) {
        this.name = name;
        this.score = score;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        NodeScore nodeScore = new NodeScore(null, 0);

        public NodeScore build() {
            return nodeScore;
        }

        public Builder setName(String name) {
            nodeScore.setName(name);
            return this;
        }

        public Builder setScore(int score) {
            nodeScore.setScore(score);
            return this;
        }

    }
}
