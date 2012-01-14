package com.mb.android.preferences.persistance

import spock.lang.Specification
import com.mb.android.preferences.persistance.KeyGenerator

class KeyGeneratorSpec extends Specification{

    KeyGenerator keyGenerator = new KeyGenerator()
    def "given an id I expect a type key to be generated"()
    {
        given:
        String id = "test"

        expect:
        keyGenerator.createTypeKey(id) == "test_type"

    }


    def "given an field id I expect a key to be generated"()
    {
        given:
        String id = "testId"
        String fieldName = "field"

        expect:
        keyGenerator.createKey(id,fieldName) == "testId_field"

    }
}
