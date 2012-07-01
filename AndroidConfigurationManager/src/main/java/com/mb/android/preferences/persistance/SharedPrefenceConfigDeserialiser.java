package com.mb.android.preferences.persistance;

import java.lang.reflect.Field;

import android.content.SharedPreferences;
import android.util.Log;

import com.mb.android.preferences.annotations.ConfigMetadata;
import com.mb.android.preferences.domain.Config;
import com.mb.android.preferences.reflection.ConfigMetadataReflector;
import com.mb.android.preferences.reflection.ConfigMetadataReflector.ConfigValueAction;

public class SharedPrefenceConfigDeserialiser implements ConfigDeserialiser {

	private final String TAG = SharedPrefenceConfigDeserialiser.class
			.getCanonicalName();

	private ConfigMetadataReflector reflector = new ConfigMetadataReflector();
	private SharedPreferences preferences;
	private KeyGenerator keyGenerator = new KeyGenerator();

	public SharedPrefenceConfigDeserialiser(SharedPreferences preferences) {
		this.preferences = preferences;
	}

	@Override
	public Config deserialise(final Config config, final String id) {
		config.setId(id);

		ConfigValueAction putEditorAction = new ConfigValueAction() {

			@Override
			public void process(Field field, ConfigMetadata fieldMetadata) {
				Object value = getPreferenceValueForField(id, fieldMetadata);
				if (value != null)
					setObjectValueForField(config, field, value);
			}

		};

		reflector.processMetadata(config, putEditorAction);

		return config;
	}

	@Override
	public String getType(String id) {
		return preferences.getString(keyGenerator.createTypeKey(id), "");
	}

	private void setObjectValueForField(Config config, Field field, Object value) {
		try {
			field.set(config, value);
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
	}

	private Object getPreferenceValueForField(String configId,
			ConfigMetadata fieldMetadata) {

		String key = keyGenerator.createKey(configId, fieldMetadata.id());
		Object value = null;

		try {
			switch (fieldMetadata.type()) {
			case String:
			case StringList:
				value = preferences.getString(key, "");
				break;
			case Boolean:
				value = preferences.getBoolean(key, false);
				break;
			}
		} catch (ClassCastException ex) {
			Log.e(TAG, "Could not deserialise '" + key + "'");
		}
		return value;
	}
}
