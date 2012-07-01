package com.mb.android.preferences.ui;

import android.content.Context;
import android.preference.CheckBoxPreference;
import android.preference.EditTextPreference;
import android.preference.ListPreference;
import android.preference.Preference;

import com.mb.android.preferences.annotations.ConfigMetadata;
import com.mb.android.preferences.ui.UIPreferenceBuilder.PreferenceFactory;

public class ActivityPreferenceFactory implements PreferenceFactory {

	private Context context;

	public ActivityPreferenceFactory(Context context) {
		this.context = context;
	}

	public Preference createPreference(ConfigMetadata metadata) {
		switch (metadata.type()) {
		case String:
			return new EditTextPreference(context);
		case Boolean:
			return new CheckBoxPreference(context);
		case StringList:
			return new ListPreference(context);
		}
		return null;
	}
}
