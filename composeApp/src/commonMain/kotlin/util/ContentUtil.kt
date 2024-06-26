package util

import java.util.regex.Matcher
import java.util.regex.Pattern

fun String.extractUrls(): List<String> {
    val containedUrls: MutableList<String> = ArrayList()
    val urlRegex =
        "((https?|http):((//)|(\\\\))+[\\w\\d:#@%/;$()~_?\\+-=\\\\\\.&]*)"
    val pattern: Pattern = Pattern.compile(urlRegex, Pattern.CASE_INSENSITIVE)
    val urlMatcher: Matcher = pattern.matcher(this)
    while (urlMatcher.find()) {
        containedUrls.add(
            this.substring(
                urlMatcher.start(0),
                urlMatcher.end(0)
            )
        )
    }
    return containedUrls
}

fun String.extractImageUrls(): List<String> {
    val containedUrls: MutableList<String> = ArrayList()
    val urlRegex =
        "((https?|http):((//)|(\\\\))+[\\w\\d:#@%/;$()~_?\\+-=\\\\\\.&]*\\.(png|jpeg|jpg|webp|webm|gif))"
    val pattern: Pattern = Pattern.compile(urlRegex, Pattern.CASE_INSENSITIVE)
    val urlMatcher: Matcher = pattern.matcher(this)
    while (urlMatcher.find()) {
        containedUrls.add(
            this.substring(
                urlMatcher.start(0),
                urlMatcher.end(0)
            )
        )
    }
    return containedUrls
}

fun String.extractHashtags(): List<String> {
    val containedUrls: MutableList<String> = ArrayList()
    val urlRegex = "#(\\S+)"
    val pattern: Pattern = Pattern.compile(urlRegex, Pattern.CASE_INSENSITIVE)
    val urlMatcher: Matcher = pattern.matcher(this)
    while (urlMatcher.find()) {
        containedUrls.add(
            this.substring(
                urlMatcher.start(0),
                urlMatcher.end(0)
            )
        )
    }
    return containedUrls
}