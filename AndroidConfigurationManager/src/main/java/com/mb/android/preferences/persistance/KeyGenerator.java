package com.mb.android.preferences.persistance;

public class KeyGenerator {

    public String createTypeKey(String id) {
        return createKey(id, "type");
    }

    public String createKey(String id, String key) {
        return id + "_" + key;
    }
}
