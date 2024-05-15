package com.flynn.feature_home.utils

import java.util.Locale

fun String.getEveryNthChar(
    step: Int,
): String {
    return this.filterIndexed { index, _ -> index % step == 0 }.drop(1)
}

fun String.countWords(): Map<String, Int> {
    val wordCounts = this
        .lowercase(Locale.getDefault()) // Make the text case insensitive
        .split("\\s+".toRegex())        // Split by any whitespace characters
        .filter { it.isNotEmpty() }     // Filter out any empty strings
        .groupingBy { it }              // Group by each unique word
        .eachCount()                    // Count the occurrences of each word

    return wordCounts
}

