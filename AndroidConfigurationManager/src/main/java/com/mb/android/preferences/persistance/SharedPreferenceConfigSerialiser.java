package com.mb.android.preferences.persistance;

import android.content.SharedPreferences.Editor;
import com.mb.android.preferences.annotations.ConfigMetadata;
import com.mb.android.preferences.domain.Config;
import com.mb.android.preferences.reflection.ConfigMetadataReflector;
import com.mb.android.preferences.reflection.ConfigMetadataReflector.ConfigValueAction;

import java.lang.reflect.Field;

public class SharedPreferenceConfigSerialiser implements ConfigSerialiser {

    private ConfigMetadataReflector reflector = new ConfigMetadataReflector();
    private final Editor editor;
    private KeyGenerator keyGenerator = new KeyGenerator();

    public SharedPreferenceConfigSerialiser(Editor editor) {
        this.editor = editor;
    }

    public void serialise(final Config config) {
        if (!config.isValid()) {
            throw new InvalidConfigDataException();
        }

        ConfigValueAction putEditorAction = new ConfigValueAction() {


            public void process(Field field, ConfigMetadata fieldMetadata) {
                String key = keyGenerator.createKey(config.getId(), fieldMetadata.id());
                Object value = getValueForField(config, field);

                SharedPreferenceConfigSerialiser.this.putEditorValue(editor, key, value);
            }

        };

        editor.putString(keyGenerator.createTypeKey(config.getId()), config.getType());
        reflector.processMetadata(config, putEditorAction);

    }


    public void delete(final Config config) {
        ConfigValueAction deleteFromEditorAction = new ConfigValueAction() {


            public void process(Field field, ConfigMetadata fieldMetadata) {
                String key = keyGenerator.createKey(config.getId(), fieldMetadata.id());
                editor.remove(key);
            }

        };

        reflector.processMetadata(config, deleteFromEditorAction);
    }

    private void putEditorValue(Editor editor, String key, Object value) {
        if (value instanceof String) {
            editor.putString(key, (String) value);
        } else if (value instanceof Boolean) {
            editor.putBoolean(key, (Boolean) value);
        }
    }

    private Object getValueForField(Config config, Field field) {
        Object value = null;
        try {
            field.setAccessible(true);
            value = field.get(config);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return value;
    }

    public static class InvalidConfigDataException extends RuntimeException {
        private static final long serialVersionUID = 3036730216021767648L;

    }

}
