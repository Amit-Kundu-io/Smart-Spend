package com.amit_kundu_io.theme.components.PlatformDatePicker

import androidx.compose.runtime.Composable
import platform.Foundation.timeIntervalSince1970
import platform.UIKit.UIAlertAction
import platform.UIKit.UIAlertActionStyleDefault
import platform.UIKit.UIAlertController
import platform.UIKit.UIAlertControllerStyleAlert
import platform.UIKit.UIApplication
import platform.UIKit.UIDatePicker
import platform.UIKit.UIDatePickerMode

@Composable
actual fun PlatformDatePicker(
    onDismiss: () -> Unit,
    onDateSelected: (Long) -> Unit
) {
    val datePicker = UIDatePicker().apply {
        datePickerMode = UIDatePickerMode.UIDatePickerModeDate
    }

    val alert = UIAlertController.alertControllerWithTitle(
        title = "Select Date",
        message = "\n\n\n\n\n\n\n\n\n",
        preferredStyle = UIAlertControllerStyleAlert
    )

    alert.view.addSubview(datePicker)

    UIAlertAction.actionWithTitle("OK", UIAlertActionStyleDefault) {
        val seconds = datePicker.date.timeIntervalSince1970
        onDateSelected((seconds * 1000).toLong()/1000)
        onDismiss()
    }.also { alert.addAction(it) }

    UIApplication.sharedApplication.keyWindow?.rootViewController
        ?.presentViewController(alert, true, null)
}