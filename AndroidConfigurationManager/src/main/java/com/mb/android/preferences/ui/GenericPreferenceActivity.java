package com.mb.android.preferences.ui;

import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceCategory;
import android.preference.PreferenceScreen;
import com.mb.android.preferences.domain.Config;
import com.mb.android.preferences.manager.ConfigManager;

import java.util.List;
import java.util.UUID;

public abstract class GenericPreferenceActivity extends PreferenceActivity {
    //private final static String TAG = GenericPreferenceActivity.class.getCanonicalName();
    public final static String ConfigCanonicalClassKey = "canonicalClass";
    public final static String ConfigIdKey = "configId";
    private final ConfigManager configManager;
    private String id = null;
    private Config c;

    public GenericPreferenceActivity(ConfigManager configManager) {
        this.configManager = configManager;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String configCanonicalClassName = getIntent().getStringExtra(ConfigCanonicalClassKey);
        getConfigClassFromIntent(configCanonicalClassName);

        if (c.getId().equals("")) {
            if (getIntent().hasExtra(ConfigIdKey)) {
                id = getIntent().getStringExtra(ConfigIdKey);
            } else {
                id = UUID.randomUUID().toString();
            }
            c.setId(id);
        } else {
            id = c.getId();
        }

        if (configManager.hasConfig(id)) {
            c = configManager.getConfig(id);
        } else {
            configManager.saveConfig(c);
        }

        PreferenceScreen ps = getPreferenceManager().createPreferenceScreen(this);
        setPreferenceScreen(ps);

        PreferenceCategory preferenceCategory = new PreferenceCategory(this);
        preferenceCategory.setTitle(c.getName() + " Settings");
        ps.addPreference(preferenceCategory);

        UIPreferenceBuilder preferenceBuilder = new UIPreferenceBuilder();
        preferenceBuilder.setPreferenceFactory(new ActivityPreferenceFactory(this));
        List<Preference> prefs = preferenceBuilder.getPreferencesFor(c);

        for (Preference preference : prefs)
            preferenceCategory.addPreference(preference);

    }

    private void getConfigClassFromIntent(String configCanonicalClassName) {
        try {
            c = (Config) Class.forName(configCanonicalClassName).newInstance();
        } catch (InstantiationException e1) {
            e1.printStackTrace();
        } catch (IllegalAccessException e1) {
            e1.printStackTrace();
        } catch (ClassNotFoundException e1) {
            e1.printStackTrace();
        }
    }

}
