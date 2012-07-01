package com.mb.android.preferences.processor

import com.mb.android.preferences.domain.Config
import com.mb.android.preferences.persistance.ConfigDeserialiser
import org.gmock.WithGMock
import spock.lang.Specification
import com.mb.android.preferences.mixin.TestConfig

@WithGMock
class DefaultConfigDeserializerStrategySpec extends Specification {


    DefaultConfigDeserializerStrategy defaultConfigDeserializerStrategy
    ConfigDeserialiser mockConfigDeserialiser
    TestConfig testConfig

    void setup() {
        testConfig = new TestConfig()
        mockConfigDeserialiser = Mock(ConfigDeserialiser)
        defaultConfigDeserializerStrategy = new DefaultConfigDeserializerStrategy<TestConfig>(mockConfigDeserialiser, testConfig)
    }


    def "given an id expect getConfig to return a populated config with the associated id"() {
        given:
        String id = "test"

        and: "setup deserialiser with the mock test config"
        defaultConfigDeserializerStrategy = new DefaultConfigDeserializerStrategy<TestConfig>(mockConfigDeserialiser, new TestConfig())

        when:
        Config returnedConfig = defaultConfigDeserializerStrategy.getConfig(id)
_
        then:
        mockConfigDeserialiser.deserialise(_, id) >> testConfig

        and:
        returnedConfig == testConfig
    }

    def "given a TestConfig concrete type the supported config type is TestConfig"() {
        expect:
        defaultConfigDeserializerStrategy.getSupportedConfigType() == testConfig.type
    }

   
}
