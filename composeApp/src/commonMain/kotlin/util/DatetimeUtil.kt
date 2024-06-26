@file:OptIn(FormatStringsInDatetimeFormats::class)

package util

import kotlinx.datetime.Instant
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.format
import kotlinx.datetime.format.DateTimeFormat
import kotlinx.datetime.format.FormatStringsInDatetimeFormats
import kotlinx.datetime.format.byUnicodePattern
import kotlinx.datetime.toLocalDateTime

private val DEFAULT_PATTERN = LocalDateTime.Format {
    byUnicodePattern("yyyy-MM-dd HH:mm")
}

private val GMT_OR_UTC by lazy {
    TimeZone.availableZoneIds
        .find { it.contains("Bangkok", ignoreCase = true) }
        ?.let { TimeZone.of(it) }
        ?: TimeZone.UTC
}

fun Long.toDate(
    format: DateTimeFormat<LocalDateTime> = DEFAULT_PATTERN,
    timeZone: TimeZone = GMT_OR_UTC
): String {
    val instant = Instant.fromEpochSeconds(this)
    return instant.toLocalDateTime(timeZone).format(format)
}