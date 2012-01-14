package com.mb.android.preferences.reflection

import com.mb.android.preferences.annotations.ConfigMetadata
import com.mb.android.preferences.domain.Config
import com.mb.android.preferences.reflection.ConfigMetadataReflector
import com.mb.android.preferences.reflection.ConfigMetadataReflector.ConfigValueAction
import java.lang.reflect.Field
import spock.lang.Specification

class ConfigMetadataReflectorSpec extends Specification {

    ConfigMetadataReflector configMetadataReflector

    def setup() {
        configMetadataReflector = new ConfigMetadataReflector()
    }


    def "given a mockClass with a field with no annotation it should not be processed"() {
        given:
        MockConfig mockClass = new MockConfig()

        and:
        ConfigValueAction action = new ConfigValueAction() {

            @Override
            void process(Field field, ConfigMetadata configMetadata) {
                if (field.name == "shouldNotProcessMe")
                    throw new Exception("shouldNotProcessMe")
            }
        }

        when:
        configMetadataReflector.processMetadata(mockClass, action)


        then:
        true == true

    }


    def "given a mockClass I expect the type field to be processed"() {
        given:
        MockConfig mockClass = new MockConfig()

        and:
        boolean iWasNotCalled = true

        and:
        ConfigValueAction action = new ConfigValueAction() {


            @Override
            void process(Field field, ConfigMetadata configMetadata) {
                if (field.name == "type")
                    iWasNotCalled = false

            }
        }

        when:
        configMetadataReflector.processMetadata(mockClass, action)


        then:
        iWasNotCalled

    }

    def "as the identify I expect the id field not to be processed"() {
        given:
        MockConfig mockClass = new MockConfig()

        and:
        boolean iWasNotCalled = true

        and:
        ConfigValueAction action = new ConfigValueAction() {


            @Override
            void process(Field field, ConfigMetadata configMetadata) {
                if (field.name == "id")
                    iWasNotCalled = false

            }
        }

        when:
        configMetadataReflector.processMetadata(mockClass, action)


        then:
        iWasNotCalled

    }

    class MockConfig extends Config {

        String shouldNotProcessMe = "testFailed"

        MockConfig() {
            this.id = "test"
        }

        @Override
        String getName() {
            return null  //To change body of implemented methods use File | Settings | File Templates.
        }
    }
}
