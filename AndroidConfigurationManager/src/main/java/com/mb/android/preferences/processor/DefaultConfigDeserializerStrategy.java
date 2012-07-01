package com.mb.android.preferences.processor;

import com.mb.android.preferences.domain.Config;
import com.mb.android.preferences.persistance.ConfigDeserialiser;

public class DefaultConfigDeserializerStrategy extends ConfigProcessorStrategy {

	final private Config config;

	public DefaultConfigDeserializerStrategy(
			ConfigDeserialiser configSerialiser, Config config) {
		super(configSerialiser);
		this.config = config;
	}

	@Override
	public String getSupportedConfigType() {
		return config.getClass().getCanonicalName();
	}

	@Override
	public Config getConfig(String id) {
		return getConfigDeserialiser().deserialise((Config) config.clone(), id);
	}

}
