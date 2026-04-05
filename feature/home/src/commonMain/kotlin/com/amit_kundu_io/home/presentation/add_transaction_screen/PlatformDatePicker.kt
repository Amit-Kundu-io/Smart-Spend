package com.amit_kundu_io.home.presentation.add_transaction_screen

import androidx.compose.runtime.Composable

@Composable
expect fun PlatformDatePicker(
    onDismiss: () -> Unit,
    onDateSelected: (Long) -> Unit
)