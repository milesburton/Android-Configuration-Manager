package com.mb.android.preferences.domain;

import android.content.Intent;

public class ConfigCategory {
	private final Intent intent;
	private String id = null;
	private final String summary;
	private final String title;
	private int drawble;

	public ConfigCategory(int drawable, Intent intent, String id, String title, String summary) {
		this(drawable, intent, title, summary);
		this.id = id;
	}

	public ConfigCategory(int drawable, Intent intent, String title, String summary) {
		this.drawble = drawable;
		this.intent = intent;
		this.title = title;
		this.summary = summary;
	}

	public Intent getIntent() {
		return intent;
	}

	public boolean hasGenericPreferenceId() {
		return id != null;
	}

	public String getGenericPreferenceId() {
		return id;
	}

	public String getSummary() {
		return summary;
	}

	public String getTitle() {
		return title;
	}

	public int getDrawble() {
		return drawble;
	}

}
