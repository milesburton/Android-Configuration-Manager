package com.mb.android.preferences.ui;

import android.preference.ListPreference;
import android.preference.Preference;
import com.mb.android.preferences.annotations.ConfigDescription;
import com.mb.android.preferences.annotations.ConfigMetadata;
import com.mb.android.preferences.annotations.ConfigOption;
import com.mb.android.preferences.annotations.ConfigOptions;
import com.mb.android.preferences.domain.Config;
import com.mb.android.preferences.persistance.KeyGenerator;
import com.mb.android.preferences.reflection.ConfigMetadataReflector;
import com.mb.android.preferences.reflection.ConfigMetadataReflector.ConfigValueAction;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class UIPreferenceBuilder {
    private KeyGenerator keyGenerator = new KeyGenerator();
    private PreferenceFactory preferenceFactory = null;

    public List<Preference> getPreferencesFor(final Config config) {
        final List<Preference> preferences = new ArrayList<Preference>();

        ConfigMetadataReflector configMetadataReflector = new ConfigMetadataReflector();

        ConfigValueAction putEditorAction = new ConfigValueAction() {


            public void process(Field field, ConfigMetadata configMetadata) {
                ConfigDescription configDescription = getConfigDescription(field);
                Preference preference = getPreferenceFor(config, configMetadata, configDescription);
                populateDefaults(field, preference);
                preferences.add(preference);
            }
        };

        configMetadataReflector.processMetadata(config, putEditorAction);
        return preferences;
    }

    private ConfigDescription getConfigDescription(Field field) {
        ConfigDescription fieldDescription = (ConfigDescription) field.getAnnotation(ConfigDescription.class);

        if (fieldDescription == null)
            throw new MissingAnnotationException();
        return fieldDescription;
    }

    private Preference getPreferenceFor(Config config, ConfigMetadata configMetadata, ConfigDescription fieldDescription) {
        String key = keyGenerator.createKey(config.getId(), configMetadata.id()); // this is wrong! shouldn't know about a key generator

        Preference preference = preferenceFactory.createPreference(configMetadata);

        preference.setKey(key);
        preference.setTitle(fieldDescription.title());
        if (!"".equals(fieldDescription.description()))
            preference.setSummary(fieldDescription.description());

        return preference;
    }

    private void populateDefaults(Field field, Preference preference) {
        if (preference instanceof ListPreference)
            populateDefaults(field, (ListPreference) preference);
    }

    private void populateDefaults(Field field, ListPreference preference) {
        ConfigOptions configOptions = (ConfigOptions) field.getAnnotation(ConfigOptions.class);
        if (configOptions == null)
            return;

        String[] titleArray = new String[configOptions.values().length];
        String[] valueArray = new String[configOptions.values().length];

        for (int i = 0; i < configOptions.values().length; i++) {
            ConfigOption configOption = configOptions.values()[i];
            titleArray[i] = configOption.title();
            valueArray[i] = configOption.value();
        }

        preference.setEntries(titleArray);
        preference.setEntryValues(valueArray);
    }

    public void setPreferenceFactory(PreferenceFactory preferenceFactory) {
        this.preferenceFactory = preferenceFactory;
    }

    public interface PreferenceFactory {
        Preference createPreference(ConfigMetadata metadata);
    }

    public static class MissingAnnotationException extends RuntimeException {
        private static final long serialVersionUID = 1922639213002486492L;
    }
}
