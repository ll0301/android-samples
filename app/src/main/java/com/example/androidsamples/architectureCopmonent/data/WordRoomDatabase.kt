package com.example.androidsamples.architectureCopmonent.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

/**
 * Room class must be abstract and extend RoomDatabase.
 * @Database -> declare the entities that belong in the database and set the version number.
 *           -> Each entity corresponds to a table that will be created in the database.
 * exportSchema -> Database migrations are beyond the scope of this code,
 *                 so we set exportSchema to false here to avoid a build warning.
 * Note : When you modify the database schema,
 *        you'll need to update the version number and define a migration strategy
 **/
@Database(entities = [Word::class], version = 1, exportSchema = false)
abstract class WordRoomDatabase : RoomDatabase() {


    // The database exposes DAOs through an abstract 'getter' method for each @Dao.
    abstract fun wordDao(): WordDao

    private class WordDatabaseCallback(
        private val scope: CoroutineScope
    ) : RoomDatabase.Callback() {

        override fun onOpen(db: SupportSQLiteDatabase) {
            super.onOpen(db)
            INSTANCE?.let { database ->
                scope.launch {
                    var wordDao = database.wordDao()

                    // Delete all content here.
                    wordDao.deleteAll()

                    // Add sample words.
                    var word = Word("Hello")
                    wordDao.insert(word)
                    word = Word("World!")
                    wordDao.insert(word)

                    // TODO: Add your own words!
                    word = Word("TODO!")
                    wordDao.insert(word)
                }
            }
        }
    }

    /**
     * getDatabase() return the singleton.
     * Create the database the first time it's accessed.
     * Using room database builder to create RoomDatabase object in the application context
     * from WordRoomDatabase class and names it "word_database"
    **/
    companion object {
        @Volatile
        private var INSTANCE: WordRoomDatabase? = null

        fun getDatabase(
            context: Context,
            scope: CoroutineScope
        ): WordRoomDatabase {
            // if the INSTANCE is not null, then return it,
            // if it is, then create the database
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    WordRoomDatabase::class.java,
                    "word_database"
                )
                    .addCallback(WordDatabaseCallback(scope))
                    .build()
                INSTANCE = instance
                // return instance
                instance
            }
        }
    }
}