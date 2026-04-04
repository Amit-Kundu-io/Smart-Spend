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


    private fun getMonthRange(): Pair<Long, Long> {
        val now = Clock.System.now()
        val zone = TimeZone.currentSystemDefault()

        val current = now.toLocalDateTime(zone)

        val startOfMonth = LocalDateTime(
            year = current.year,
            month = current.month,
            dayOfMonth = 1,
            hour = 0,
            minute = 0
        )

        val endOfMonth = startOfMonth
            .date
            .plus(1, DateTimeUnit.MONTH)
            .minus(1, DateTimeUnit.DAY)
            .atTime(23, 59, 59)

        val startMillis = startOfMonth.toInstant(zone).toEpochMilliseconds()
        val endMillis = endOfMonth.toInstant(zone).toEpochMilliseconds()

        return startMillis to endMillis
    }

    fun getCurrentMonth(): Int {
        val now = Clock.System.now()
        val zone = TimeZone.currentSystemDefault()
        return now.toLocalDateTime(zone).monthNumber
    }

    fun getCurrentYear(): Int {
        val now = Clock.System.now()
        val zone = TimeZone.currentSystemDefault()
        return now.toLocalDateTime(zone).year
    }

    fun getMonthStartEpoch(): Long {
        val now = Clock.System.now()
        val zone = TimeZone.currentSystemDefault()

        val current = now.toLocalDateTime(zone)

        val startOfMonth = LocalDateTime(
            year = current.year,
            month = current.month,
            dayOfMonth = 1,
            hour = 0,
            minute = 0,
            second = 0
        )

        return startOfMonth.toInstant(zone).epochSeconds
    }
    fun getMonthEndEpoch(): Long {
        val now = Clock.System.now()
        val zone = TimeZone.currentSystemDefault()

        val current = now.toLocalDateTime(zone)

        val startOfNextMonth = LocalDate(
            year = current.year,
            month = current.month,
            dayOfMonth = 1
        ).plus(1, DateTimeUnit.MONTH)

        val endOfMonth = startOfNextMonth
            .minus(1, DateTimeUnit.DAY)
            .atTime(23, 59, 59)

        return endOfMonth.toInstant(zone).epochSeconds
    }

    fun getMonthYearLabel(): String {
        val now = Clock.System.now()
        val zone = TimeZone.currentSystemDefault()

        val current = now.toLocalDateTime(zone)

        val monthName = current.month.name
            .lowercase()
            .replaceFirstChar { it.uppercase() }
            .take(3)

        return "$monthName ${current.year}"
    }


    fun getCurrentWeekStartEpoch(): Long {
        val zone = TimeZone.currentSystemDefault()

        val today = Clock.System.now()
            .toLocalDateTime(zone)
            .date

        val daysFromMonday = today.dayOfWeek.ordinal // Monday = 0

        val startOfWeek = today.minus(daysFromMonday, DateTimeUnit.DAY)

        return startOfWeek
            .atStartOfDayIn(zone)
            .toEpochMilliseconds() / 1000
    }

    fun getCurrentWeekEndEpoch(): Long {
        val zone = TimeZone.currentSystemDefault()

        val start = Instant
            .fromEpochMilliseconds(getCurrentWeekStartEpoch() * 1000)
            .toLocalDateTime(zone)
            .date

        val endOfWeek = start.plus(6, DateTimeUnit.DAY)

        return endOfWeek
            .atTime(23, 59, 59)
            .toInstant(zone)
            .toEpochMilliseconds() / 1000
    }

    fun currentMonthRange(): Pair<Long, Long> {
        val now = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault())
        val year = now.year
        val month = now.month

        //  Calculate last day of month
        val lastDayOfMonth = when (month) {
            Month.JANUARY, Month.MARCH, Month.MAY, Month.JULY,
            Month.AUGUST, Month.OCTOBER, Month.DECEMBER -> 31
            Month.APRIL, Month.JUNE, Month.SEPTEMBER, Month.NOVEMBER -> 30
            Month.FEBRUARY -> if (isLeapYear(year)) 29 else 28
        }

        //  Start of month
        val startMillis = LocalDate(year, month, 1)
            .atStartOfDayIn(TimeZone.currentSystemDefault())
            .toEpochMilliseconds()

        //  End of month
        val endMillis = LocalDate(year, month, lastDayOfMonth)
            .atTime(LocalTime(23, 59, 59))
            .toInstant(TimeZone.currentSystemDefault())
            .toEpochMilliseconds()

        // Convert millis → seconds (10 digits)
        return Pair(startMillis / 1000, endMillis / 1000)
    }

    private fun isLeapYear(year: Int): Boolean {
        return (year % 4 == 0 && year % 100 != 0) || (year % 400 == 0)
    }

    fun currentWeekRange(): Pair<Long, Long> {
        val tz = TimeZone.currentSystemDefault()
        val now = Clock.System.now().toLocalDateTime(tz)

        // Day of week index (Monday = 1 … Sunday = 7)
        val dayOfWeek = now.date.dayOfWeek.isoDayNumber

        // Start of week = subtract (dayOfWeek - 1) days
        val startDate = now.date.minus(dayOfWeek - 1, DateTimeUnit.DAY)
        val startMillis = startDate.atStartOfDayIn(tz).toEpochMilliseconds() / 1000

        // End of week = add (7 - dayOfWeek) days
        val endDate = now.date.plus(7 - dayOfWeek, DateTimeUnit.DAY)
        val endMillis = endDate.atTime(LocalTime(23, 59, 59))
            .toInstant(tz)
            .toEpochMilliseconds() / 1000

        return startMillis to endMillis
    }

}