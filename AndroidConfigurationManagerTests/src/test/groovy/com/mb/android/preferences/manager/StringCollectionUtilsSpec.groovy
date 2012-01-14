package com.mb.android.preferences.manager

import spock.lang.Specification

class StringCollectionUtilsSpec extends Specification {

    def "given a list of strings and a comma join character a single string should be returned in csv format"() {
        given:
        List<String> listOfStrings = ["one", "two", "three"]

        when:
        String csv = StringCollectionUtils.join(listOfStrings, ",")

        then:
        csv == "one,two,three"

    }


    def "given a string list with one element and a comma join character a single string should be returned in csv format"() {
        given:
        List<String> listOfStrings = ["one"]

        when:
        String csv = StringCollectionUtils.join(listOfStrings, ",")

        then:
        csv == "one"
    }

    def "given an empty string list with one element and a comma join character a single string should be returned in csv format"() {
        given:
        List<String> listOfStrings = []

        when:
        String csv = StringCollectionUtils.join(listOfStrings, ",")

        then:
        csv == ""
    }

    def "given a csv string a set should be returned"() {
        given:
        String csvString = "one,two,three,two"

        when:
        Set<String> stringSet = StringCollectionUtils.convertToSet(csvString, ",")

        then:
        stringSet.contains("two")
        stringSet.contains("one")
        stringSet.contains("three")


    }


}
