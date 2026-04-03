package com.amit_kundu_io.smartspend

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.amit_kundu_io.home.presentation.home_screen.HomeRootScreen
import com.amit_kundu_io.theme.ui.SmartSpendTheme
import org.jetbrains.compose.resources.painterResource

import smartspend.composeapp.generated.resources.Res
import smartspend.composeapp.generated.resources.compose_multiplatform

@Composable
@Preview
fun App() {
    SmartSpendTheme {
        HomeRootScreen()
    }
}