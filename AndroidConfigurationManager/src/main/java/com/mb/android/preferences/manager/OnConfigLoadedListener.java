package com.mb.android.preferences.manager;

import com.mb.android.preferences.domain.Config;

import java.util.Map;

public interface OnConfigLoadedListener {
    void configLoaded(Map<String, Config> configuration);
}
