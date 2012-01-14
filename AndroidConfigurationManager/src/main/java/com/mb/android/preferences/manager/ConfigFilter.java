package com.mb.android.preferences.manager;

import com.mb.android.preferences.domain.Config;

public interface ConfigFilter {
    boolean isMatch(Config config);
}
