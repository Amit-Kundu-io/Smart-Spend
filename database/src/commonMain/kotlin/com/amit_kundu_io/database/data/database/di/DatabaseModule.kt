package com.amit_kundu_io.database.data.database.di

import com.amit_kundu_io.database.data.RepoImpl.BudgetRepositoryImpl
import com.amit_kundu_io.database.data.RepoImpl.TransactionRepositoryImpl
import com.amit_kundu_io.database.data.database.AppDatabase
import com.amit_kundu_io.database.data.database.DatabaseFactory
import com.amit_kundu_io.database.data.database.dao.BudgetDao
import com.amit_kundu_io.database.data.database.dao.TransactionDao
import com.amit_kundu_io.database.domain.Repo.BudgetRepository
import com.amit_kundu_io.database.domain.Repo.TransactionRepository
import org.koin.dsl.module

fun getDatabaseModule() = module {

    includes(playroomDi())

    // Database Factory
    single {
        DatabaseFactory(get())
    }

    // Database instance (Singleton)
    single<AppDatabase> {
        get<DatabaseFactory>().create()
    }

    // DAO
    single<TransactionDao> {
        get<AppDatabase>().transactionDao()
    }

    single<BudgetDao> {
        get<AppDatabase>().budgetDao()
    }

    single<TransactionRepository> {
        TransactionRepositoryImpl(get())
    }

    single<BudgetRepository> {
        BudgetRepositoryImpl(get())
    }
}