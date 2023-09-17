package com.lgypro.j8s.framework.plugins.tainttoleration;

import com.lgypro.j8s.api.core.v1.Toleration;
import com.lgypro.j8s.framework.StateData;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class PreScoreState implements StateData {

    List<Toleration> tolerationsPreferNoSchedule;

    public PreScoreState(List<Toleration> tolerations) {
        this.tolerationsPreferNoSchedule = tolerations;
    }
}
