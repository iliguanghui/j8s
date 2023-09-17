package com.lgypro.j8s.framework;

import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * // CycleState provides a mechanism for plugins to store and retrieve arbitrary data.
 * // StateData stored by one plugin can be read, altered, or deleted by another plugin.
 * // CycleState does not provide any data protection, as all plugins are assumed to be
 * // trusted.
 * // Note: CycleState uses a sync.Map to back the storage. It's aimed to optimize for the "write once and read many times" scenarios.
 * // It is the recommended pattern used in all in-tree plugins - plugin-specific state is written once in PreFilter/PreScore and afterwards read many times in Filter/Score.
 */
public class CycleState {
    ConcurrentHashMap<String, StateData> storage = new ConcurrentHashMap<>();

    boolean recordPluginMetrics;

    Set<String> skipFilterPlugins;
    Set<String> skipScorePlugins;

    public StateData read(String key) {
        return storage.get(key);
    }

    public void write(String key, StateData data) {
        storage.put(key, data);
    }

    public void delete(String key) {
        storage.remove(key);
    }
}
