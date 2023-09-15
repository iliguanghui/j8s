package com.lgypro.j8s.framework;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GroupVersionKind {

    public static final GroupVersionKind Pod = of("", "v1", "Pod");
    public static final GroupVersionKind Node = of("", "v1", "Node");
    public static final GroupVersionKind WildCard = of("*", "*", "*");

    String group;
    String version;
    String kind;

    public GroupVersionKind(String group, String version, String kind) {
        this.group = group;
        this.version = version;
        this.kind = kind;
    }

    public static GroupVersionKind of(String group, String version, String kind) {
        return new GroupVersionKind(group, version, kind);
    }
}
