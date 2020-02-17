package com.tapan.architectureDemo.core.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.tapan.architectureDemo.dictionary.data.datasource.DictionaryDAO
import com.tapan.architectureDemo.dictionary.model.DictionaryEntity

@Database(entities = [DictionaryEntity::class], version = 1)
abstract class AppDataBase : RoomDatabase() {
    abstract fun dictionaryDAO(): DictionaryDAO
}