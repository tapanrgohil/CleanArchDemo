package com.tapan.architectureDemo.core.di

import androidx.room.Room
import com.tapan.architectureDemo.core.data.AppDataBase
import com.tapan.architectureDemo.dictionary.data.ResultRepository
import com.tapan.architectureDemo.dictionary.data.ResultRepositoryImpl
import com.tapan.architectureDemo.dictionary.data.datasource.*
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

/**
 * koin module for data process
 */
val dataModule = module {
    single<ResultRepository> {
        ResultRepositoryImpl(
            get(),
            get(),
            get()
        )
    }

    single<RemoteDataSource> { RemoteDataSourceImpl(get()) }
    single<LocalDataSource> { LocalDataSourceImpl(get()) }



}


val databaseModule = module {
    single {
        Room.databaseBuilder(androidContext(), AppDataBase::class.java, "DictionaryAppDB")
            .fallbackToDestructiveMigration()//for testing use only will use migration in production
            .build()
    }
    single { get<AppDataBase>().dictionaryDAO() }

}

val testDataBaseModules = module {
    single {
        Room.inMemoryDatabaseBuilder(androidContext(),
            AppDataBase::class.java)
            .allowMainThreadQueries()
            .build()
    }
    single { get<AppDataBase>().dictionaryDAO() }

}