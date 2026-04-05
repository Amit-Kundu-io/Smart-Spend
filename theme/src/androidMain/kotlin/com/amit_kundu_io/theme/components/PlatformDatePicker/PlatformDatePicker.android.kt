package com.amit_kundu_io.theme.components.PlatformDatePicker

import android.app.DatePickerDialog
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

    DatePickerDialog(
        context,
        { _, year, month, dayOfMonth ->
            val cal = Calendar.getInstance().apply {
                set(year, month, dayOfMonth, 12, 0, 0) // ✅ MIDDAY (IMPORTANT)
                set(Calendar.MILLISECOND, 0)
            }

            val epochSeconds = cal.timeInMillis / 1000
            onDateSelected(epochSeconds)
        },
        calendar.get(Calendar.YEAR),
        calendar.get(Calendar.MONTH),
        calendar.get(Calendar.DAY_OF_MONTH)
    ).apply {
        setOnDismissListener { onDismiss() }
    }.show()
}