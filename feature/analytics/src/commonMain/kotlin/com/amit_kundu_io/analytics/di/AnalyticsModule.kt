/**
 * AnalyticsModule.kt
 *
 * Author      : Amit Kundu
 * Created On  : 04/04/2026
 *
 * Description :
 * Part of the project codebase. This file contributes to the overall
 * functionality and follows standard coding practices and architecture.
 *
 * Notes :
 * Ensure changes are consistent with project guidelines and maintain
 * code readability and quality.
 */

package com.amit_kundu_io.analytics.di

import com.amit_kundu_io.analytics.presentation.analytics_screen.AnalyticsViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

fun analyticsModule() = module {
    viewModelOf(::AnalyticsViewModel)
}
