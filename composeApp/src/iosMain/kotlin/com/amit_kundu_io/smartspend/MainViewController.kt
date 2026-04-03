package com.amit_kundu_io.smartspend

import androidx.compose.ui.window.ComposeUIViewController
import com.amit_kundu_io.smartspend.root_di.initKoin

fun MainViewController() = ComposeUIViewController (
    configure = {
        initKoin()
    }
){ App() }