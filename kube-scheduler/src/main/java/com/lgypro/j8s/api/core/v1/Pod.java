package com.lgypro.j8s.api.core.v1;

import com.lgypro.j8s.apis.meta.v1.ObjectMeta;
import com.lgypro.j8s.apis.meta.v1.TypeMeta;
import lombok.Getter;
import lombok.Setter;

public class Pod {
    TypeMeta typeMeta;
    ObjectMeta objectMeta;
    @Getter
    @Setter
    PodSpec spec;
    @Getter
    @Setter
    PodStatus status;

    public Pod() {
        spec = new PodSpec();
    }
}
