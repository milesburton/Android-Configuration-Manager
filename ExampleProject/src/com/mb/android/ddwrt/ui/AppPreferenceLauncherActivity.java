package com.mb.android.ddwrt.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.MenuItem;


import com.mb.android.ddwrt.app.domain.RouterConfig;
import com.mb.android.preferences.domain.ConfigCategory;
import com.mb.android.preferences.ui.GenericPreferenceActivity;
import com.mb.android.preferences.ui.PreferenceLauncherActivity;

public class AppPreferenceLauncherActivity extends PreferenceLauncherActivity {

	public void onCreate(Bundle icicle) {
		super.onCreate(icicle);
		Intent routerSetup = new Intent(this, GeneralConfigActivity.class).putExtra(GenericPreferenceActivity.ConfigCanonicalClassKey, RouterConfig.class.getCanonicalName());
		addCategoryList(new ConfigCategory(android.R.drawable.ic_menu_agenda, routerSetup, "Router setup", "Setup your router configuration"));
	}

	@Override
	public void onStart() {
		super.onStart();
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
	}

	public boolean onOptionsItemSelected(MenuItem item) {

		return super.onOptionsItemSelected(item);
	}
}
