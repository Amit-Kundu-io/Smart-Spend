package com.amit_kundu_io.home.presentation.add_transaction_screen.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.amit_kundu_io.home.presentation.add_transaction_screen.AddTransactionAction
import com.amit_kundu_io.theme.components.PlatformDatePicker.PlatformDatePicker
import kotlinx.datetime.toLocalDateTime

@Composable
fun DatePickerField(
    label: String = "Select Date",
    onDateSelected: (Long) -> Unit,
    date: Long
) {
    val openPicker = remember { mutableStateOf(false) }

    fun formatDate(epochSeconds: Long): String {
        val instant = kotlinx.datetime.Instant.fromEpochSeconds(epochSeconds)
        val localDateTime =
            instant.toLocalDateTime(kotlinx.datetime.TimeZone.currentSystemDefault())
        return "${localDateTime.dayOfMonth}/${localDateTime.monthNumber}/${localDateTime.year}"
    }

    var selectedText by remember(date) {
        mutableStateOf(
            if (date != 0L) formatDate(date) else ""
        )
    }

    if (openPicker.value) {
        PlatformDatePicker(
            onDismiss = { openPicker.value = false },
            onDateSelected = { epochSeconds ->
                selectedText = formatDate(epochSeconds)
                onDateSelected(epochSeconds)
                openPicker.value = false
            }
        )
    }

    Text(
        label,
        style = MaterialTheme.typography.labelMedium,
        color = MaterialTheme.colorScheme.onSurfaceVariant
    )

    // Wrap with Box (IMPORTANT FIX)
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { openPicker.value = true }
    ) {
        OutlinedTextField(
            value = selectedText,
            onValueChange = {},
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(12.dp),
            singleLine = true,
            enabled = false,
            colors = OutlinedTextFieldDefaults.colors(
                disabledTextColor = MaterialTheme.colorScheme.onSurface,
                disabledBorderColor = MaterialTheme.colorScheme.outline,
                disabledLabelColor = MaterialTheme.colorScheme.onSurfaceVariant,
                disabledPlaceholderColor = MaterialTheme.colorScheme.onSurfaceVariant,
                disabledContainerColor = MaterialTheme.colorScheme.surface
            )
        )
    }
}