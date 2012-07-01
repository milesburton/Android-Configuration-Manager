package com.mb.android.ddwrt.ui;

import com.mb.android.ddwrt.app.App;
import com.mb.android.preferences.ui.GenericPreferenceActivity;

import android.support.v4.view.MenuItem;


public class GeneralConfigActivity extends GenericPreferenceActivity {

	public GeneralConfigActivity() {
		super(App.get().getConfigManager());
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
