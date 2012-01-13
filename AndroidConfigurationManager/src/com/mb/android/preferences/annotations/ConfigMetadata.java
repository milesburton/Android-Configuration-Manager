package com.mb.android.preferences.annotations;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface ConfigMetadata {
	String id();

	boolean required();

	PreferenceType type();
}
