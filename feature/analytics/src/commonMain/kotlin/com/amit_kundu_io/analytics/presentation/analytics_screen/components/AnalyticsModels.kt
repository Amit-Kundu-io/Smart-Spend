package com.amit_kundu_io.analytics.presentation.analytics_screen.components

import androidx.compose.ui.graphics.Color

enum class AnalyticsPeriod(val label: String) {
    WEEK("Week"), MONTH("Month"), THREE_MONTHS("3M"), YEAR("Year")
}

data class BarData(
    val label: String,
    val value: Float,
    val isHighlighted: Boolean = false,
    val isFaded: Boolean = false
)

data class CategoryData(
    val emoji: String,
    val name: String,
    val amount: Long,
    val percentage: Float,
    val color: Color
)

data class MonthSummary(
    val name: String,
    val subtitle: String,
    val amount: Long,
    val changePercent: Int
)

data class AnalyticsUiState(
    val period: AnalyticsPeriod = AnalyticsPeriod.WEEK,
    val totalSpent: Long = 0,
    val kpi2Label: String = "",
    val kpi2Value: String = "",
    val kpi3Label: String = "",
    val kpi3Value: String = "",
    val kpi3IsPositive: Boolean = false,
    val bars: List<BarData> = emptyList(),
    val categories: List<CategoryData> = emptyList(),
    val stats: List<Pair<String, String>> = emptyList(),
    val weeklyBars: List<Pair<String, Long>> = emptyList(),
    val heatmapDays: List<Float> = emptyList(),
    val monthSummaries: List<MonthSummary> = emptyList(),
    val quarters: List<Triple<String, String, Long?>> = emptyList()
)

private val teal = Color(0xFF006874)
private val purple = Color(0xFF4527A0)
private val purple2 = Color(0xFF7B1FA2)
private val green = Color(0xFF2E7D32)
private val orange = Color(0xFFE65100)

fun analyticsStateFor(period: AnalyticsPeriod): AnalyticsUiState = when (period) {
    AnalyticsPeriod.WEEK -> AnalyticsUiState(
        period = period,
        totalSpent = 5840,
        kpi2Label = "AVG/DAY", kpi2Value = "₹834",
        kpi3Label = "VS LAST WK", kpi3Value = "-12%", kpi3IsPositive = false,
        bars = listOf(
            BarData("Mon", 450f), BarData("Tue", 680f),
            BarData("Wed", 1200f, isHighlighted = true),
            BarData("Thu", 380f), BarData("Fri", 600f),
            BarData("Sat", 440f), BarData("Sun", 280f)
        ),
        categories = listOf(
            CategoryData("🍽", "Food & Dining", 7200, 0.43f, purple),
            CategoryData("🛍", "Shopping", 5900, 0.35f, purple2),
            CategoryData("🚗", "Transport", 3800, 0.23f, teal),
            CategoryData("⚡", "Utilities", 2400, 0.14f, green),
            CategoryData("🎮", "Entertainment", 1850, 0.11f, orange),
        ),
        stats = listOf(
            "This Month" to "₹22,150",
            "Daily Avg" to "₹716",
            "Savings" to "₹42,850",
            "Transactions" to "47"
        )
    )

    AnalyticsPeriod.MONTH -> AnalyticsUiState(
        period = period,
        totalSpent = 22150,
        kpi2Label = "AVG/DAY", kpi2Value = "₹716",
        kpi3Label = "VS LAST MO", kpi3Value = "+8%", kpi3IsPositive = true,
        heatmapDays = listOf(
            0f, 0.3f, 0.6f, 0.9f, 0.3f, 0.8f, 0.3f,
            0.6f, 0.3f, 0.7f, 1.0f, 0.3f, 0.5f, 0.3f,
            0.3f, 0.7f, 0.5f, 0.3f, 0.85f, 0.3f, 0.5f,
            0.3f, 0.5f, 0.3f, 0.7f, 0.3f, 1.0f, 0.3f,
            0.5f, 0.3f, 0.7f
        ),
        weeklyBars = listOf("Wk 1" to 5840L, "Wk 2" to 7200L, "Wk 3" to 4510L, "Wk 4" to 4600L),
        categories = listOf(
            CategoryData("🍽", "Food & Dining", 9400, 0.42f, purple),
            CategoryData("🛍", "Shopping", 7200, 0.32f, purple2),
            CategoryData("🚗", "Transport", 5550, 0.25f, teal),
        ),
        stats = listOf(
            "Total Spent" to "₹22,150",
            "Daily Avg" to "₹716",
            "Highest Day" to "₹2,840",
            "Transactions" to "47"
        )
    )

    AnalyticsPeriod.THREE_MONTHS -> AnalyticsUiState(
        period = period,
        totalSpent = 64320,
        kpi2Label = "AVG/MONTH", kpi2Value = "₹21.4K",
        kpi3Label = "TREND", kpi3Value = "-5%", kpi3IsPositive = false,
        bars = listOf(
            BarData("Feb", 20480f),
            BarData("Mar", 21690f),
            BarData("Apr", 22150f, isHighlighted = true)
        ),
        monthSummaries = listOf(
            MonthSummary("February 2026", "28 days · 42 transactions", 20480, -3),
            MonthSummary("March 2026", "31 days · 51 transactions", 21690, 6),
            MonthSummary("April 2026", "30 days · 47 transactions", 22150, 2),
        ),
        categories = listOf(
            CategoryData("🍽", "Food & Dining", 26400, 0.55f, purple),
            CategoryData("🛍", "Shopping", 19200, 0.40f, purple2),
            CategoryData("🚗", "Transport", 11400, 0.24f, teal),
        ),
        stats = listOf(
            "3M Total" to "₹64,320",
            "Avg/Month" to "₹21.4K",
            "Best Month" to "Feb",
            "Total TX" to "140"
        )
    )

    AnalyticsPeriod.YEAR -> AnalyticsUiState(
        period = period,
        totalSpent = 80480,
        kpi2Label = "AVG/MONTH", kpi2Value = "₹20.2K",
        kpi3Label = "VS LAST YR", kpi3Value = "+14%", kpi3IsPositive = true,
        bars = listOf(
            BarData("J", 19200f), BarData("F", 16800f), BarData("M", 21700f),
            BarData("A", 22150f, isHighlighted = true),
            BarData("M", 0f, isFaded = true), BarData("J", 0f, isFaded = true),
            BarData("J", 0f, isFaded = true), BarData("A", 0f, isFaded = true),
            BarData("S", 0f, isFaded = true), BarData("O", 0f, isFaded = true),
            BarData("N", 0f, isFaded = true), BarData("D", 0f, isFaded = true),
        ),
        quarters = listOf(
            Triple("Q1", "Jan–Mar", 57700L),
            Triple("Q2", "Apr only", 22150L),
            Triple("Q3", "Jul–Sep", null),
            Triple("Q4", "Oct–Dec", null),
        ),
        categories = listOf(
            CategoryData("🍽", "Food & Dining", 92400, 0.55f, purple),
            CategoryData("🛍", "Shopping", 68800, 0.41f, purple2),
            CategoryData("🚗", "Transport", 41200, 0.25f, teal),
            CategoryData("⚡", "Utilities", 24000, 0.14f, green),
        ),
        stats = listOf(
            "YTD Total" to "₹80,480",
            "Avg/Month" to "₹20.1K",
            "Best Month" to "Feb",
            "Total TX" to "140"
        )
    )
}
