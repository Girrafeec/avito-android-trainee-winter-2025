package com.girrafeec.avito_deezer.util

import android.annotation.SuppressLint
import androidx.compose.runtime.Stable
import java.text.DecimalFormat
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle
import kotlin.time.Duration
import kotlin.time.Duration.Companion.seconds

object DateTime {

    @SuppressLint("NewApi")
    fun getDateTime(
        timestampMillis: Long,
        todayString: String? = null,
        todayFormat: Format? = null,
        yesterdayString: String? = null,
        yesterdayFormat: Format? = null,
        thisYearFormat: Format? = null,
        otherFormat: Format = Format.Style(
            dateStyle = FormatStyle.LONG,
            timeStyle = FormatStyle.LONG,
        ),
    ): String {
        val localDateTime = timestampMillis.toZonedDateTime().toLocalDateTime()
        return when {
            localDateTime.isToday() && todayString != null -> todayString
            localDateTime.isToday() && todayFormat != null -> {
                todayFormat.toDateTimeFormatter().format(localDateTime)
            }

            localDateTime.isYesterday() && yesterdayString != null -> yesterdayString
            localDateTime.isYesterday() && yesterdayFormat != null -> {
                yesterdayFormat.toDateTimeFormatter().format(localDateTime)
            }

            localDateTime.isThisYear() && thisYearFormat != null -> {
                thisYearFormat.toDateTimeFormatter().format(localDateTime)
            }

            else -> otherFormat.toDateTimeFormatter().format(localDateTime)
        }
    }

    fun durationToString(duration: Duration): String {
        return duration
            .coerceAtLeast(Duration.ZERO)
            .plus(DURATION_THRESHOLD)
            .toComponents { hours, minutes, seconds, _ ->
                val hh = TWO_DIGITS_FORMAT.format(hours)
                val mm = TWO_DIGITS_FORMAT.format(minutes)
                val ss = TWO_DIGITS_FORMAT.format(seconds)
                if (hours != 0L) "$hh:$mm:$ss" else "$mm:$ss"
            }
    }

    @Stable
    sealed interface Format {
        data class Style(val dateStyle: FormatStyle?, val timeStyle: FormatStyle?) : Format

        data class Pattern(val pattern: String) : Format
    }

    @SuppressLint("NewApi")
    private fun Format.toDateTimeFormatter(): DateTimeFormatter {
        return when (this) {
            is Format.Style -> {
                when {
                    dateStyle != null && timeStyle != null -> {
                        DateTimeFormatter.ofLocalizedDateTime(dateStyle, timeStyle)
                    }

                    dateStyle != null -> DateTimeFormatter.ofLocalizedDate(dateStyle)
                    else -> DateTimeFormatter.ofLocalizedTime(timeStyle)
                }
            }

            is Format.Pattern -> DateTimeFormatter.ofPattern(pattern)
        }
    }

    private val TWO_DIGITS_FORMAT = DecimalFormat("00")
    private val DURATION_THRESHOLD = 0.5.seconds
}

@SuppressLint("NewApi")
fun Long.toZonedDateTime(): ZonedDateTime {
    return Instant
        .ofEpochMilli(this)
        .atZone(ZoneId.systemDefault())
}

@SuppressLint("NewApi")
fun LocalDateTime.isToday(): Boolean {
    val now = LocalDateTime.now()
    return dayOfYear == now.dayOfYear && year == now.year
}

@SuppressLint("NewApi")
fun LocalDateTime.isYesterday(): Boolean {
    val now = LocalDateTime.now()
    return dayOfYear == now.dayOfYear - 1 && year == year
}

@SuppressLint("NewApi")
fun LocalDateTime.isThisYear(): Boolean {
    val now = LocalDateTime.now()
    return year == now.year
}
