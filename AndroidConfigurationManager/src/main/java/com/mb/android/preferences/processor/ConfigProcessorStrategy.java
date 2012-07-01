package com.mb.android.preferences.processor;

import com.mb.android.preferences.domain.Config;
import com.mb.android.preferences.persistance.ConfigDeserialiser;

public abstract class ConfigProcessorStrategy {

    private final ConfigDeserialiser configDeserialiser;

    public ConfigProcessorStrategy(ConfigDeserialiser configDeserialiser) {

        this.configDeserialiser = configDeserialiser;
    }

    public abstract String getSupportedConfigType();

    public abstract Config getConfig(String id);

    protected ConfigDeserialiser getConfigDeserialiser() {
        return configDeserialiser;
    }
}
