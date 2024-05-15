package com.flynn.feature_home

import com.flynn.feature_home.utils.countWords
import com.flynn.feature_home.utils.getEveryNthChar
import org.junit.Test

import org.junit.Assert.*

class StringExtensionsTest {

    @Test
    fun testGetEveryNthChar() {
        val input = "abcdefghijklmnopqrstuvwxyz"

        val result1 = input.getEveryNthChar(3)
        assertEquals("dgjmpsvy", result1)

        val emptyString = ""
        val result3 = emptyString.getEveryNthChar(10)
        assertEquals("", result3)

        val result4 = input.getEveryNthChar(10)
        assertEquals("ku", result4)
    }

    @Test
    fun testCountWords() {
        val input = "This is a test. This test is only a test.\n" +
                "<!DOCTYPE html>\n" +
                "<html lang=\"en\">\n"

        val expected = mapOf(
            "this" to 2,
            "is" to 2,
            "a" to 2,
            "test." to 2,
            "test" to 1,
            "only" to 1,
            "<!doctype" to 1,
            "html>" to 1,
            "<html" to 1,
            "lang=\"en\">" to 1,
        )

        val result = input.countWords()
        assertEquals(expected, result)
    }

    @Test
    fun testEmptyStringCountWords() {
        val emptyString = ""
        val result2 = emptyString.countWords()
        assertEquals(emptyMap<String, Int>(), result2)
    }

    @Test
    fun testOneWordCountWords() {
        val singleWord = "word"
        val result3 = singleWord.countWords()
        assertEquals(mapOf("word" to 1), result3)
    }
}