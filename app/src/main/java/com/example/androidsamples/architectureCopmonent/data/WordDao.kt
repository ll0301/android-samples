package com.example.androidsamples.architectureCopmonent.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

/**
 * Dao must either be interfaces or abstract classes.
 * @Dao -> Identifies it as a DAO class for Room.
 * @Insert -> Special DAO method annotation where you don't have to provide any SQL!
 *         -> There are similar @Update and @Delete
 * OnConflictStrategy.IGNORE -> The selected onConflict strategy ignores a new word if it's exactly the same as one already in the list.
 * deleteAll() -> There is no convenience annotation for deleting multiple entities, So it is implemented as @Query
 * @Query -> Query requires that you provide a SQL query as a string parameter to the annotation.
 *        -> Should know how to use basic SQLite grammar.
 **/
@Dao
interface WordDao {

    /**
     * Set getAlphabetizedWords() method signature so that the returned `List<Word>` is wrapped with LiveData.
     * That LiveData is can track data changes via an Observer in UI Controller.
     **/
    @Query("SELECT * from word_table ORDER BY word ASC")
    fun getAlphabetizedWords(): LiveData<List<Word>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(word: Word)

    @Query("DELETE FROM word_table")
    suspend fun deleteAll()
}