package com.lgypro.j8s.framework;

import java.util.List;

public interface NodeInfoLister {
    NodeInfo get(String nodeName);
    List<NodeInfo> list();
}
