package com.mb.android.preferences.manager;

import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.util.Log;
import com.mb.android.preferences.domain.Config;
import com.mb.android.preferences.persistance.ConfigDeserialiser;
import com.mb.android.preferences.persistance.ConfigSerialiser;
import com.mb.android.preferences.persistance.SharedPrefenceConfigDeserialiser;
import com.mb.android.preferences.persistance.SharedPreferenceConfigSerialiser;
import com.mb.android.preferences.processor.ConfigProcessorStrategy;

import java.util.*;
import java.util.Map.Entry;

public class ConfigManager implements OnSharedPreferenceChangeListener {
    private final String TAG = ConfigManager.class.getCanonicalName();
    private final String CONFIG_ID_LIST_KEY = "configIdList";
    private final SharedPreferences preferences;
    private Map<String, ConfigProcessorStrategy> configProcessorStragegyList = new HashMap<String, ConfigProcessorStrategy>();
    private Map<String, Config> configMap = new HashMap<String, Config>();
    private List<OnConfigLoadedListener> configLoadedListeners = new ArrayList<OnConfigLoadedListener>();

    public ConfigManager(SharedPreferences preferences) {
        this.preferences = preferences;
        this.preferences.registerOnSharedPreferenceChangeListener(this);
    }

    public void saveConfig(Config config) {
        String id = config.getId();
        Set<String> newIdList = getConfigIdList();
        newIdList.add(id);

        Editor editor = preferences.edit();
        editor.putString(CONFIG_ID_LIST_KEY, StringCollectionUtils.join(newIdList, ","));
        ConfigSerialiser configSerialiser = new SharedPreferenceConfigSerialiser(editor);
        configSerialiser.serialise(config);
        commitPreferenceChange(editor);
    }

    public void loadFullConfig() {
        for (String configId : getConfigIdList())
            processConfig(configId);

        triggerOnConfigLoadedEvent();
    }

    public void listenForConfigLoaded(OnConfigLoadedListener listener) {
        configLoadedListeners.add(listener);
    }

    public SharedPreferences getSharedPreferences() {
        return preferences;
    }

    public void removeConfig(Config config) {
        String id = config.getId();
        Set<String> newIdList = getConfigIdList();
        newIdList.remove(id);
        configMap.remove(config.getId());

        Editor editor = preferences.edit();
        editor.putString(CONFIG_ID_LIST_KEY, StringCollectionUtils.join(newIdList, ","));
        ConfigSerialiser configSerialiser = new SharedPreferenceConfigSerialiser(editor);
        configSerialiser.delete(config);
        commitPreferenceChange(editor);
    }

    public Config getConfig(String configId) {
        return configMap.get(configId);
    }

    public boolean hasConfig(String configId) {
        return configMap.containsKey(configId);
    }

    public Map<String, Config> getConfigurationFor(ConfigFilter filter) {
        Map<String, Config> configuration = new HashMap<String, Config>();
        for (Entry<String, Config> configEntry : configMap.entrySet()) {
            if (filter.isMatch(configEntry.getValue())) {
                configuration.put(configEntry.getKey(), configEntry.getValue());
            }
        }
        return configuration;
    }


    public void onSharedPreferenceChanged(SharedPreferences arg0, String arg1) {
        loadFullConfig();
    }

    private void processConfig(String configId) {

        ConfigDeserialiser configSerialiser = new SharedPrefenceConfigDeserialiser(preferences);
        String type = configSerialiser.getType(configId);

        if (!isInvalidConfig(type)) {
            ConfigProcessorStrategy configProcessor = configProcessorStragegyList.get(type);
            if (configProcessor == null) {
                Log.w(TAG, "Cannot process config with type: " + type);
            } else {
                Config config = configProcessor.getConfig(configId);
                configMap.put(configId, config);
            }
        }
    }

    private Set<String> getConfigIdList() {
        String csv = preferences.getString(CONFIG_ID_LIST_KEY, "");
        return StringCollectionUtils.convertToSet(csv, ",");
    }

    private boolean isInvalidConfig(String type) {
        return type == null || "".equals(type);
    }

    private void triggerOnConfigLoadedEvent() {
        for (OnConfigLoadedListener listener : configLoadedListeners) {
            listener.configLoaded(configMap);
        }
    }

    private void commitPreferenceChange(Editor editor) {
        if (!editor.commit())
            throw new FailedToSaveException();
    }

    public void addConfigProcessorStrategy(ConfigProcessorStrategy configProcessorStrategy) {
        this.configProcessorStragegyList.put(configProcessorStrategy.getSupportedConfigType(), configProcessorStrategy);
    }

    public static class FailedToSaveException extends RuntimeException {

        private static final long serialVersionUID = 4439373387523867367L;

    }

}
