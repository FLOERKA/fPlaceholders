package ru.floerka.placeholders.script.models;

import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

public class ScriptArgs {

    @Getter
    private final Map<String, Object> map;

    public ScriptArgs() {
        this.map = new HashMap<>();
    }
    public static ScriptArgs create() {
        return new ScriptArgs();
    }
    public ScriptArgs add(String key, Object value) {
        map.put(key ,value);
        return this;
    }
    public ScriptArgs remove(String key) {
        map.remove(key);
        return this;
    }
    public Object get(String key) {
        return map.get(key);
    }

}
