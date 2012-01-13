package com.mb.android.preferences.reflection

import com.mb.android.preferences.domain.Config
import com.mb.android.preferences.reflection.FieldReflector
import java.lang.reflect.Field
import spock.lang.Specification

class FieldReflectorSpec extends Specification {

    def NUMBEROFGROOVYMETACLASSES = 7

    def "Given a MockClass with four fields I then four fields are returned"() {
        given:
        FieldReflector fieldReflector = new FieldReflector()

        when:
        List<Field> fieldList = fieldReflector.getFields(MockClass)
        
        then:
        fieldList.size() == (4 + NUMBEROFGROOVYMETACLASSES)
        fieldList.findAll { it.name == "test1"}.size() == 1
        fieldList.findAll { it.name == "test2"}.size() == 1
        fieldList.findAll { it.name == "id"}.size() == 1
        fieldList.findAll { it.name == "type"}.size() == 1
    }


    class MockClass extends Config {

        private String test1
        private String test2

        @Override
        String getName() {
            return null  //To change body of implemented methods use File | Settings | File Templates.
        }
    }

}
