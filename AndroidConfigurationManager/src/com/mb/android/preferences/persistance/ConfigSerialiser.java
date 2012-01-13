package com.mb.android.preferences.persistance;

import com.mb.android.preferences.domain.Config;

public interface ConfigSerialiser {
	void serialise(final Config config);

	void delete(final Config config);
}
