package util

import kotlin.test.Test
import kotlin.test.assertEquals

class UrlUtilTest {

    @Test
    fun a() {
        val source = "Welcome to https://stackoverflow.com/ and here is another link http://www.google.com/ \n which is a great search engine"
        val extractedUrls = source.extractImageUrls()
        assertEquals("https://stackoverflow.com/", extractedUrls[0])
        assertEquals("http://www.google.com/", extractedUrls[1])
    }
}