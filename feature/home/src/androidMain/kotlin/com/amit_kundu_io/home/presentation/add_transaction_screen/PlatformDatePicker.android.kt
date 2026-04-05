package com.amit_kundu_io.home.presentation.add_transaction_screen

import androidx.compose.material3.DatePickerDialog
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import java.util.Calendar

@Composable
actual fun PlatformDatePicker(
    onDismiss: () -> Unit,
    onDateSelected: (Long) -> Unit
) {
    val context = LocalContext.current
    val calendar = Calendar.getInstance()

    val value = DatePickerDialog(
        context,
        { _, year, month, dayOfMonth ->
            val cal = Calendar.getInstance().apply {
                set(year, month, dayOfMonth, 0, 0, 0)
                set(Calendar.MILLISECOND, 0)
            }
            onDateSelected(cal.timeInMillis)
        },
        calendar.get(Calendar.YEAR),
        calendar.get(Calendar.MONTH),
        calendar.get(Calendar.DAY_OF_MONTH)
    ).apply {
        setOnDismissListener { onDismiss() }
    }.show()
}