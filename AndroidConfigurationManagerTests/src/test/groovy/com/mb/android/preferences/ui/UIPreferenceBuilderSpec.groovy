package com.mb.android.preferences.ui

import android.app.Activity
import android.preference.EditTextPreference
import android.preference.Preference
import com.mb.android.preferences.annotations.ConfigMetadata
import com.mb.android.preferences.annotations.PreferenceType
import com.mb.android.preferences.mixin.TestConfig
import org.gmock.WithGMock
import spock.lang.Specification
import android.preference.CheckBoxPreference
import android.preference.ListPreference

@WithGMock
class UIPreferenceBuilderSpec extends Specification {

    UIPreferenceBuilder uiPreferenceBuilder

    void setup() {
        uiPreferenceBuilder = new UIPreferenceBuilder()
    }


    def "Given a config return a preference list which represents each configurable field"() {
        given:
        Activity mockContext = mock(Activity)
        ActivityPreferenceFactory preferenceFactory = mock(ActivityPreferenceFactory)
        uiPreferenceBuilder.setPreferenceFactory(preferenceFactory)

        TestConfig config = new TestConfig()

        and: "our key is test"
        config.id = "test"
        
        and: "prepare mock preferenceDialogs"
        EditTextPreference editTextPreference = mock(EditTextPreference)
        CheckBoxPreference checkBoxPreference = mock(CheckBoxPreference)
        ListPreference listPreference = mock(ListPreference)
        

        and: "mock out preference ui components"
        preferenceFactory.createPreference(match {ConfigMetadata configMetadata -> configMetadata.type() == PreferenceType.String}).returns(editTextPreference)
        preferenceFactory.createPreference(match {ConfigMetadata configMetadata -> configMetadata.type() == PreferenceType.Boolean}).returns(checkBoxPreference)
        preferenceFactory.createPreference(match {ConfigMetadata configMetadata -> configMetadata.type() == PreferenceType.StringList}).returns(listPreference)

        and: "given a preferencetype of string annotation set editText accordingly"
        editTextPreference.setKey("test_testString").stub()
        editTextPreference.setTitle("testString title").stub()
        editTextPreference.setSummary("testString description").stub()

        and: "given a preferencetype of boolean annotation set checkbox preference accordingly"
        checkBoxPreference.setKey("test_testBoolean").stub()
        checkBoxPreference.setTitle("testBoolean title").stub()
        checkBoxPreference.setSummary("testBoolean description").stub()

        and: "given a preferencetype of stringList annotation set editText preference accordingly"
        listPreference.setKey("test_testStringList").stub()
        listPreference.setTitle("testStringList title").stub()
        listPreference.setSummary("testStringList description").stub()
        listPreference.setEntries(["string entry title", "string entry title 2"]).stub()
        listPreference.setEntryValues(["string entry", "string entry 2"]).stub()


        when:
        List<Preference> preferenceList
        play {
            preferenceList = uiPreferenceBuilder.getPreferencesFor(config)
        }

        then:
        preferenceList == [editTextPreference, checkBoxPreference, listPreference]
    }
}
