package com.mb.android.preferences.mixin

import com.mb.android.preferences.domain.Config
import com.mb.android.preferences.annotations.ConfigMetadata
import com.mb.android.preferences.annotations.PreferenceType
import com.mb.android.preferences.annotations.ConfigDescription
import com.mb.android.preferences.annotations.ConfigOptions
import com.mb.android.preferences.annotations.ConfigOption

class TestConfig extends Config {
    @ConfigDescription(title="testString title", description="testString description")
    @ConfigMetadata(id = "testString", required = true, type = PreferenceType.String)
    String testStringField

    @ConfigDescription(title="testBoolean title", description="testBoolean description")
    @ConfigMetadata(id = "testBoolean", required = true, type = PreferenceType.Boolean)
    Boolean testBooleanField = false

    @ConfigDescription(title="testStringList title", description="testStringList description")
    @ConfigMetadata(id = "testStringList", required = true, type = PreferenceType.StringList)
    @ConfigOptions(values = [@ConfigOption(title = "string entry title", value = "string entry"), @ConfigOption(title = "string entry title 2", value = "string entry 2")])
    String testStringListField

    @Override
    String getName() {
        return null
    }
}