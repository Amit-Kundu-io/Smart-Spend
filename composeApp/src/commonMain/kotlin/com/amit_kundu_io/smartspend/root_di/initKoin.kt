package com.amit_kundu_io.smartspend.root_di

import com.amit_kundu_io.home.di.homeDi
import org.koin.core.KoinApplication
import org.koin.core.context.startKoin

fun initKoin(
    application: (KoinApplication.() -> Unit)? = null
) {
    startKoin {
        application?.invoke(this)
        modules(
            homeDi()
        )
    }
}