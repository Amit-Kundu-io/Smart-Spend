package com.amit_kundu_io.utilities.Logger

expect object Logger {
    fun d(tag: String, message: String)
    fun e(tag: String, message: String, throwable: Throwable? = null)
}