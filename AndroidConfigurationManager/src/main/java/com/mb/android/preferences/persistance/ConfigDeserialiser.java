package com.mb.android.preferences.persistance;

import com.mb.android.preferences.domain.Config;

public interface ConfigDeserialiser {
    Config deserialise(final Config config, final String id);

    String getType(final String id);
}
