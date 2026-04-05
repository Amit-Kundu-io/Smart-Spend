package com.amit_kundu_io.home.presentation.add_transaction_screen

@Composable
actual fun PlatformDatePicker(
    onDismiss: () -> Unit,
    onDateSelected: (Long) -> Unit
) {
    val datePicker = UIDatePicker().apply {
        datePickerMode = UIDatePickerModeDate
    }

    val alert = UIAlertController.alertControllerWithTitle(
        title = "Select Date",
        message = "\n\n\n\n\n\n\n\n\n",
        preferredStyle = UIAlertControllerStyleAlert
    )

    alert.view.addSubview(datePicker)

    UIAlertAction.actionWithTitle("OK", UIAlertActionStyleDefault) {
        val seconds = datePicker.date.timeIntervalSince1970
        onDateSelected((seconds * 1000).toLong())
        onDismiss()
    }.also { alert.addAction(it) }

    UIApplication.sharedApplication.keyWindow?.rootViewController
        ?.presentViewController(alert, true, null)
}