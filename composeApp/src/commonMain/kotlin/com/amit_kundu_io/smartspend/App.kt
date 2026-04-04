package com.amit_kundu_io.smartspend

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.amit_kindu_io.bottomNavigation.DashboardScreen
import com.amit_kundu_io.theme.ui.GradientStart
import com.amit_kundu_io.theme.ui.SmartSpendTheme

@Composable
@Preview
fun App() {
    SmartSpendTheme {
        Box(modifier = Modifier.fillMaxSize().background(GradientStart)) {
            DashboardScreen()
        }
    }
}