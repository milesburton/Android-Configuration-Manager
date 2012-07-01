package com.mb.android.preferences.reflection;

import com.mb.android.preferences.annotations.ConfigIdentity;
import com.mb.android.preferences.annotations.ConfigMetadata;
import com.mb.android.preferences.domain.Config;

import java.lang.reflect.Field;
import java.util.List;

public class ConfigMetadataReflector {
    public void processMetadata(Config config, ConfigValueAction action) {
        List<Field> fields = new FieldReflector().getFields(config.getClass());
        for (Field field : fields) {
            ConfigMetadata fieldMetadata = (ConfigMetadata) field.getAnnotation(ConfigMetadata.class);

            if (hasMetadata(fieldMetadata) && !isIdentityOrType(field)) {
                action.process(field, fieldMetadata);
            }
        }
    }

    private boolean hasMetadata(ConfigMetadata fieldMetadata) {
        return fieldMetadata != null;
    }

    private boolean isIdentityOrType(Field field) {
        ConfigMetadata fieldMetadata = (ConfigMetadata) field.getAnnotation(ConfigMetadata.class);
        return field.getAnnotation(ConfigIdentity.class) != null || fieldMetadata.id().equals("type");
    }

    public interface ConfigValueAction {
        void process(Field field, ConfigMetadata fieldMetadata);
    }
}
