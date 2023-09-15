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
}
