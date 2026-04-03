package com.amit_kundu_io.utilities.global_utility

import platform.Foundation.NSUUID

actual object PlatformGlobulUtility {
    actual fun generateUUID(): String = NSUUID().UUIDString
}