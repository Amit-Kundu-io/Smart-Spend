package com.amit_kundu_io.smartspend.root_di

import com.amit_kundu_io.database.data.database.di.getDatabaseModule
import com.amit_kundu_io.home.di.homeDi
import com.amit_kundu_io.transactions.di.transactionsModules
import org.koin.core.KoinApplication
import org.koin.core.context.startKoin

fun initKoin(
    application: (KoinApplication.() -> Unit)? = null
) {
    startKoin {
        application?.invoke(this)
        modules(
            homeDi(),
            getDatabaseModule(),
            transactionsModules(),
        )
    }
}