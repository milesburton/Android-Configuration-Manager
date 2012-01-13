package com.mb.android.preferences.persistance

import android.content.SharedPreferences
import com.mb.android.preferences.annotations.ConfigMetadata
import com.mb.android.preferences.annotations.PreferenceType
import com.mb.android.preferences.domain.Config
import org.gmock.WithGMock
import spock.lang.Specification
import com.mb.android.preferences.mixin.TestConfig

@WithGMock
class SharedPreferenceConfigSerialiserSpec extends Specification {


    def setup() {

    }

    def "given an invalid config the serialiser throws an exception"() {
        given:
        TestConfig testConfig = new TestConfig()

        and:
        def editorMock = mock(SharedPreferences.Editor)
        def sharedPreferenceConfigSerialiser = new SharedPreferenceConfigSerialiser(editorMock)

        when:
        sharedPreferenceConfigSerialiser.serialise(testConfig)

        then:
        thrown(SharedPreferenceConfigSerialiser.InvalidConfigDataException)
    }



    def "given a configuration object a representation should be stored in a shared preference"() {
        given: "a populated config object"
        TestConfig testConfig = new TestConfig()
        testConfig.id = "test"
        testConfig.testStringField = "stringTest"
        testConfig.testBooleanField = true
        testConfig.testStringListField = "stringListValue"

        and:
        def editorMock = mock(SharedPreferences.Editor)
        def sharedPreferenceConfigSerialiser = new SharedPreferenceConfigSerialiser(editorMock)

        and: "SharedPreferenceConfigSerialiser has attempted to populate the shared preferenced"
        editorMock.putString("test_type", testConfig.type).atLeastOnce()
        editorMock.putString("test_testString", testConfig.testStringField).atLeastOnce()
        editorMock.putBoolean("test_testBoolean", testConfig.testBooleanField).atLeastOnce()
        editorMock.putString("test_testStringList", testConfig.testStringListField).atLeastOnce()

        when:
        play {
            sharedPreferenceConfigSerialiser.serialise(testConfig)
        }

        then:
        true == true
    }
}
