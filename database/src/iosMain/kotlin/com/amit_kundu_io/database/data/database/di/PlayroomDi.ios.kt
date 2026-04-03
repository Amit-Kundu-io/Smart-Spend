package com.amit_kundu_io.database.data.database.di

import com.amit_kundu_io.database.data.database.DatabaseBuilderFactory
import org.koin.dsl.module

actual fun playroomDi() = module {

    single {
        DatabaseBuilderFactory()
    }
}