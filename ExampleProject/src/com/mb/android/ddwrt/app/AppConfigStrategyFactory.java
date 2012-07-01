package com.mb.android.ddwrt.app;

import java.util.ArrayList;
import java.util.List;

import android.content.SharedPreferences;

import com.mb.android.ddwrt.app.domain.RouterConfig;
import com.mb.android.preferences.persistance.ConfigDeserialiser;
import com.mb.android.preferences.persistance.SharedPrefenceConfigDeserialiser;
import com.mb.android.preferences.processor.ConfigProcessorStrategy;
import com.mb.android.preferences.processor.DefaultConfigDeserializerStrategy;

public class AppConfigStrategyFactory {
	private final ConfigDeserialiser deserialiser;

	public AppConfigStrategyFactory(SharedPreferences preferences) {
		this.deserialiser = new SharedPrefenceConfigDeserialiser(preferences);
	}

	public List<ConfigProcessorStrategy> getSupportedConfigStrategies() {
		
		List<ConfigProcessorStrategy> configProcessorMap = new ArrayList<ConfigProcessorStrategy>();
		RouterConfig generalConfig = new RouterConfig();
		configProcessorMap.add(new DefaultConfigDeserializerStrategy(deserialiser, generalConfig));

		return configProcessorMap;
	}

}
