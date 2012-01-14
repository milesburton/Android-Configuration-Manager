package com.mb.android.preferences.persistance;

import android.content.SharedPreferences;
import com.mb.android.preferences.annotations.ConfigMetadata;
import com.mb.android.preferences.domain.Config;
import com.mb.android.preferences.reflection.ConfigMetadataReflector;
import com.mb.android.preferences.reflection.ConfigMetadataReflector.ConfigValueAction;

import java.lang.reflect.Field;

public class SharedPrefenceConfigDeserialiser implements ConfigDeserialiser {

    private ConfigMetadataReflector reflector = new ConfigMetadataReflector();
    private SharedPreferences preferences;
    private KeyGenerator keyGenerator = new KeyGenerator();

    public SharedPrefenceConfigDeserialiser(SharedPreferences preferences) {
        this.preferences = preferences;
    }

    public Config deserialise(final Config config, final String id) {
        config.setId(id);

        ConfigValueAction putEditorAction = new ConfigValueAction() {


            public void process(Field field, ConfigMetadata fieldMetadata) {
                Object value = getPreferenceValueForField(id, fieldMetadata);
                if (value != null)
                    setObjectValueForField(config, field, value);
            }

        };

        reflector.processMetadata(config, putEditorAction);

        return config;
    }

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

    private Object getPreferenceValueForField(String configId, ConfigMetadata fieldMetadata) {
        Object value = null;
        switch (fieldMetadata.type()) {
            case String:
            case StringList:
                value = preferences.getString(keyGenerator.createKey(configId, fieldMetadata.id()), "");
                break;
            case Boolean:
                value = preferences.getBoolean(keyGenerator.createKey(configId, fieldMetadata.id()), false);
                break;
        }
        return value;
    }

}
