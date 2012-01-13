package com.mb.android.preferences.persistance

import android.content.SharedPreferences
import com.mb.android.preferences.annotations.ConfigMetadata
import com.mb.android.preferences.annotations.PreferenceType
import com.mb.android.preferences.domain.Config
import com.mb.android.preferences.persistance.SharedPrefenceConfigDeserialiser
import spock.lang.Specification
import com.mb.android.preferences.mixin.TestConfig

class SharedPreferenceConfigDeserialiserSpec extends Specification {

    SharedPrefenceConfigDeserialiser sharedPrefenceConfigDeserialiser

    def setup() {
        sharedPrefenceConfigDeserialiser = new SharedPrefenceConfigDeserialiser(sharedPreferences)
    }


    def "given a mock shared preference I expect mockConfig to be populated"() {
        given:
        String id = "test"
        TestConfig mockConfig = new TestConfig()

        when:

        !mockConfig.testStringField
        !mockConfig.testBooleanField
        !mockConfig.id
        !mockConfig.testStringListField

        and:
        TestConfig populatedConfig = (TestConfig) sharedPrefenceConfigDeserialiser.deserialise(mockConfig, id)

        then:
        populatedConfig.id == id
        populatedConfig.testStringField == "populated"
        populatedConfig.testBooleanField
        populatedConfig.testStringListField == "populated"
    }

    def "given an id I expect a type to be generated"() {
        given:
        String id = "test"

        expect:
        sharedPrefenceConfigDeserialiser.getType(id) == "testType"

    }

    SharedPreferences sharedPreferences = new SharedPreferences() {
        @Override
        Map<String, ?> getAll() {
            return null
        }

        @Override
        String getString(String key, String defValue) {
            if (key == "test_testString" || key == "test_testStringList")
                return "populated"

            else if (key == "test_type")
                return "testType"
            else
                return defValue
        }

        @Override
        int getInt(String key, int defValue) {
            return 0
        }

        @Override
        long getLong(String key, long defValue) {
            return 0
        }

        @Override
        float getFloat(String key, float defValue) {
            return 0
        }

        @Override
        boolean getBoolean(String key, boolean defValue) {
            if (key == "test_testBoolean")
                return true
            else
                return defValue
        }

        @Override
        boolean contains(String key) {
            return false
        }

        @Override
        SharedPreferences.Editor edit() {
            return null
        }

        @Override
        void registerOnSharedPreferenceChangeListener(SharedPreferences.OnSharedPreferenceChangeListener listener) {

        }

        @Override
        void unregisterOnSharedPreferenceChangeListener(SharedPreferences.OnSharedPreferenceChangeListener listener) {

        }
    }
}
