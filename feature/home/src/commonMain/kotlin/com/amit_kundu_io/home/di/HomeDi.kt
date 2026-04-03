/**
 * HomeDi.kt
 *
 * Author      : Amit Kundu
 * Created On  : 03/04/2026
 *
 * Description :
 * Part of the project codebase. This file contributes to the overall
 * functionality and follows standard coding practices and architecture.
 *
 * Notes :
 * Ensure changes are consistent with project guidelines and maintain
 * code readability and quality.
 */

package com.amit_kundu_io.home.di

import com.amit_kundu_io.home.presentation.home_screen.HomeViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

fun homeDi() = module {
    viewModelOf(::HomeViewModel)
}