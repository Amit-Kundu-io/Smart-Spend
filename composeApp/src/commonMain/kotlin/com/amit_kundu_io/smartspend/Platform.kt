package com.amit_kundu_io.smartspend

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform