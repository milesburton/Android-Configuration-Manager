package com.mb.android.preferences.domain;

import com.mb.android.preferences.annotations.ConfigIdentity;
import com.mb.android.preferences.annotations.ConfigMetadata;
import com.mb.android.preferences.annotations.PreferenceType;

public abstract class Config implements Cloneable {

	@ConfigIdentity()
	@ConfigMetadata(id = "id", required = true, type = PreferenceType.String)
	private String id = "";

	@ConfigMetadata(id = "type", required = true, type = PreferenceType.String)
	private String type = "";

	public Config() {
		type = this.getClass().getCanonicalName();
	}

	public boolean isValid() {
		return (!isNullOrEmpty(getId()) && !isNullOrEmpty(getType()));
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getId() {
		return id;
	}

	public String getType() {
		return type;
	}

	public abstract String getName();

	public Object clone() {
		Object clone = null;
		try {
			clone = super.clone();
			if (clone == this)
				throw new Exception("");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return clone;
	}

	private boolean isNullOrEmpty(String string) {
		return "".equals(string) || string == null;
	}

}
