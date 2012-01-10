package com.mb.android.preferences.manager

import android.content.SharedPreferences
import com.mb.android.preferences.annotations.ConfigMetadata
import com.mb.android.preferences.annotations.PreferenceType
import com.mb.android.preferences.domain.Config
import com.mb.android.preferences.persistance.SharedPrefenceConfigDeserialiser
import com.mb.android.preferences.processor.DefaultConfigDeserializerStrategy
import org.gmock.WithGMock
import spock.lang.Specification
import com.mb.android.preferences.mixin.TestConfig

@WithGMock
class ConfigManagerTests extends Specification {

    ConfigManager configManager
    SharedPreferences mockSharedPreferences

    void setup() {

        mockSharedPreferences = Mock(SharedPreferences)
        configManager = new ConfigManager(mockSharedPreferences)
    }

    def "saving a configuration should persist in shared preferences"() {
        given: "prepare test config for persistance"
        TestConfig testConfig = new TestConfig()
        testConfig.id = "test"
        testConfig.testStringField = "stringField"
        testConfig.testBooleanField = true
        testConfig.testStringListField = "stringListField"

        and: "SharedPreferenceConfigSerialiser has attempted to populate the shared preferenced"
        def editorMock = mock(SharedPreferences.Editor)


        editorMock.putString("configIdList", "test").once()
        editorMock.putString("test_type", testConfig.type).once()
        editorMock.putString("test_testString", testConfig.testStringField).once()
        editorMock.putBoolean("test_testBoolean", testConfig.testBooleanField).once()
        editorMock.putString("test_testStringList", testConfig.testStringListField).once()
        editorMock.commit().returns(true).once()

        when:

        play {
            configManager.saveConfig(testConfig)
        }

        then:
        mockSharedPreferences.getString("configIdList", "") >> ""
        mockSharedPreferences.edit() >> editorMock
    }

    def "If an error during a commit of new config an exception should be thrown"() {
        given: "prepare test config for persistance"
        TestConfig testConfig = new TestConfig()
        testConfig.id = "test"
        testConfig.testStringField = "stringField"
        testConfig.testBooleanField = true
        testConfig.testStringListField = "stringListField"

        and: "SharedPreferenceConfigSerialiser has attempted to populate the shared preferenced"
        def editorMock = mock(SharedPreferences.Editor)


        editorMock.putString("configIdList", "test").once()
        editorMock.putString("test_type", testConfig.type).once()
        editorMock.putString("test_testString", testConfig.testStringField).once()
        editorMock.putBoolean("test_testBoolean", testConfig.testBooleanField).once()
        editorMock.putString("test_testStringList", testConfig.testStringListField).once()
        editorMock.commit().returns(false).once()

        when:

        play {
            configManager.saveConfig(testConfig)
        }

        then:
        mockSharedPreferences.getString("configIdList", "") >> ""
        mockSharedPreferences.edit() >> editorMock
        thrown(ConfigManager.FailedToSaveException)

    }

    def "given a persisted config the manager can load full config"() {
        given: "mock out shared preferences and ignore callback registration"
        mockSharedPreferences = mock(SharedPreferences)
        mockSharedPreferences.registerOnSharedPreferenceChangeListener(match {true})

        and: "prepare an example config for comparison"
        TestConfig exampleConfig = new TestConfig()
        exampleConfig.id = "test"
        exampleConfig.testStringField = "stringField"
        exampleConfig.testBooleanField = true
        exampleConfig.testStringListField = "stringListField"

        and: "use default config deserialiser"
        DefaultConfigDeserializerStrategy defaultConfigDeserializerStrategy = new DefaultConfigDeserializerStrategy(new SharedPrefenceConfigDeserialiser(mockSharedPreferences), exampleConfig)

        and: "saved config list should contain the example config id"
        mockSharedPreferences.getString("configIdList", "").returns(exampleConfig.id).once()

        and: "shared preferences should return a persisted config"
        mockSharedPreferences.getString("test_type", "").returns(exampleConfig.type).once()
        mockSharedPreferences.getString("test_testString", "").returns(exampleConfig.testStringField).once()
        mockSharedPreferences.getString("test_testStringList", "").returns(exampleConfig.testStringListField).once()
        mockSharedPreferences.getBoolean("test_testBoolean", false).returns(exampleConfig.testBooleanField).once()

        when: "the config manager should deserialise all configs from the shared preferences"
        play {
            configManager = new ConfigManager(mockSharedPreferences)
            configManager.addConfigProcessorStrategy(defaultConfigDeserializerStrategy)
            configManager.loadFullConfig()
            configManager.hasConfig(exampleConfig.id)
        }

        then: "should be able to return the example config"
        TestConfig deserialisedConfig = (TestConfig) configManager.getConfig(exampleConfig.id)

        and: "it should be the same as the example config"
        deserialisedConfig.id == exampleConfig.id
        deserialisedConfig.testStringField == exampleConfig.testStringField
        deserialisedConfig.testBooleanField == exampleConfig.testBooleanField
        deserialisedConfig.testStringListField == exampleConfig.testStringListField
    }

    def "once a full config has loaded the configLoaded event should be triggered"() {
        given:
        OnConfigLoadedListener mockListener = mock(OnConfigLoadedListener)

        when:
        play {
            configManager.loadFullConfig()
        }

        then: "saved config list should contain the example config id"
        mockSharedPreferences.getString("configIdList", "") >> ""

        and:
        mockListener.configLoaded(match {true}).once()
    }


}

