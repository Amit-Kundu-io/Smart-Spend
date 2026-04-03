package com.amit_kundu_io.utilities.global_utility

import java.util.UUID

actual object PlatformGlobulUtility {
    actual fun generateUUID(): String = UUID.randomUUID().toString()
}