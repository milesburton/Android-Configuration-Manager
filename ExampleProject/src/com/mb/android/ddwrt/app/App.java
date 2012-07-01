package com.mb.android.ddwrt.app;

import android.app.Application;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.mb.android.ddwrt.app.domain.RouterConfig;
import com.mb.android.preferences.manager.ConfigManager;
import com.mb.android.preferences.processor.ConfigProcessorStrategy;

public class App extends Application {

	private static App instance;
	private ConfigManager configManager;
	private boolean firstRun = true;
	private final String FIRST_RUN_KEY = "firstRun";

	public static App get() {
		return instance;
	}

	@Override
	public void onCreate() {
		super.onCreate();
		instance = this;

		SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
		setupFirstRun(sharedPreferences);

		configManager = new ConfigManager(sharedPreferences);
		setupConfigManager();
		getConfigManager().loadFullConfig();
	}

	private void setupFirstRun(SharedPreferences sharedPreferences) {
		firstRun = sharedPreferences.getBoolean(FIRST_RUN_KEY, true);
		if (firstRun)
			sharedPreferences.edit().putBoolean(FIRST_RUN_KEY, false).commit();
	}

	private void setupConfigManager() {
		AppConfigStrategyFactory AirConfigStrategyManager = new AppConfigStrategyFactory(configManager.getSharedPreferences());

		for (ConfigProcessorStrategy strategy : AirConfigStrategyManager.getSupportedConfigStrategies()) {
			configManager.addConfigProcessorStrategy(strategy);
		}
	}

	public ConfigManager getConfigManager() {
		return configManager;
	}

	public RouterConfig getRouterConfig() {
		return (RouterConfig) configManager.getConfig(RouterConfig.ID);
	}

	public int getVersionCode() {
		int versionCode = 0;
		try {
			versionCode = getApplicationContext().getPackageManager().getPackageInfo("com.mb.android.ddwrt", 0).versionCode;
			return versionCode;
		} catch (Exception ex) {

		}
		return -1;
	}

	public String getDefaultSharedPreferencesName() {
		return getApplicationContext().getPackageName() + "_preferences";
	}

	public boolean isFirstRun() {
		boolean firstRunCopy = firstRun;
		firstRun = false;
		return firstRunCopy;
	}

}
