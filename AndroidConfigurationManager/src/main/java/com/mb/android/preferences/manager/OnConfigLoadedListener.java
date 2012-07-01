package com.mb.android.preferences.manager;

import java.util.Map;

import com.mb.android.preferences.domain.Config;

public interface OnConfigLoadedListener {
	void configLoaded(Map<String, Config> configuration);
}
