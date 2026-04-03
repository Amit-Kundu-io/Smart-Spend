/**
 * GlobalUtility.kt
 *
 * Author      : Amit Kundu
 * Created On  : 02/04/2026
 *
 * Description :
 * Part of the project codebase. This file contributes to the overall
 * functionality and follows standard coding practices and architecture.
 *
 * Notes :
 * Ensure changes are consistent with project guidelines and maintain
 * code readability and quality.
 */

package com.amit_kundu_io.utilities.global_utility

import kotlin.time.Clock
import kotlinx.datetime.*

object GlobalUtility {


    fun formatCurrency(amount: Double): String {
        val rounded = kotlin.math.round(amount).toLong()
        val str = rounded.toString()

        return when {
            str.length <= 3 -> str
            else -> {
                val last3 = str.takeLast(3)
                val rest = str.dropLast(3)
                    .reversed()
                    .chunked(2)
                    .joinToString(",")
                    .reversed()
                "$rest,$last3"
            }
        }
    }

    fun currentEpochSeconds(): Long {
        return Clock.System.now().epochSeconds
    }
    fun epochToSmartDate(epochSeconds: Long): String {
        val instant = Instant.fromEpochSeconds(epochSeconds)
        val localDate = instant.toLocalDateTime(TimeZone.currentSystemDefault()).date

        val today = Clock.System.todayIn(TimeZone.currentSystemDefault())
        val yesterday = today.minus(DatePeriod(days = 1))

        return when (localDate) {
            today -> "Today"
            yesterday -> "Yesterday"
            else -> {
                val month = localDate.month.name
                    .lowercase()
                    .replaceFirstChar { it.uppercase() }
                    .take(3)

                "${localDate.dayOfMonth} $month"
            }
        }
    }

    fun epochToTime(epochSeconds: Long): String {
        val instant = Instant.fromEpochSeconds(epochSeconds)
        val dateTime = instant.toLocalDateTime(TimeZone.currentSystemDefault())

        val hour = dateTime.hour
        val minute = dateTime.minute

        val amPm = if (hour >= 12) "PM" else "AM"

        val formattedHour = when {
            hour == 0 -> 12
            hour > 12 -> hour - 12
            else -> hour
        }

        val formattedMinute = minute.toString().padStart(2, '0')

        return "$formattedHour:$formattedMinute $amPm"
    }

    fun epochToFullDate(epochSeconds: Long): String {
        val instant = Instant.fromEpochSeconds(epochSeconds)
        val date = instant.toLocalDateTime(TimeZone.currentSystemDefault()).date

        return "${date.dayOfMonth} ${date.month.name.take(3)} ${date.year}"
    }
}