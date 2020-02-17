package com.tapan.architectureDemo.dictionary.data.datasource

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.tapan.architectureDemo.dictionary.model.DictionaryEntity


@Dao
interface DictionaryDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertDictionary(dictionaryEntity: DictionaryEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertDictionary(dictionaryEntity: List<DictionaryEntity>)

    @Query("SELECT * FROM dictionary  WHERE word LIKE :term")
    suspend fun findResult(term: String): List<DictionaryEntity>
}