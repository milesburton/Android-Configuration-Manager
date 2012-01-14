package com.mb.android.preferences.domain

import com.mb.android.preferences.annotations.ConfigMetadata
import com.mb.android.preferences.annotations.PreferenceType
import spock.lang.Specification
import com.mb.android.preferences.mixin.TestConfig

class ConfigSpec extends Specification {

    def "a clone of a config object should create a new, cloned object"() {
        given:
        TestConfig testConfig = new TestConfig()
        testConfig.testBooleanField = true
        testConfig.testStringField = "changed"

        when:
        TestConfig clonedTestConfig = (TestConfig) testConfig.clone()

        then:
        clonedTestConfig != testConfig
        clonedTestConfig.testBooleanField
        clonedTestConfig.testStringField
    }


    def "a config object without a id or type should not be valid"(){
        given:
        TestConfig testConfig = new TestConfig()

        expect:
        !testConfig.valid
    }

    def "a config object with a id and is valid"(){
        given:
        TestConfig testConfig = new TestConfig()
        testConfig.id = "test"

        expect:
        testConfig.valid
    }
}
