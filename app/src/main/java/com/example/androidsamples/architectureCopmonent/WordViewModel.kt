package com.example.androidsamples.architectureCopmonent

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.androidsamples.architectureCopmonent.data.Word
import com.example.androidsamples.architectureCopmonent.data.WordRepository
import com.example.androidsamples.architectureCopmonent.data.WordRoomDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

/**
 * gets the Applications as a parameter and extends AndroidViewModel.
 *
 **/
class WordViewModel(application: Application) : AndroidViewModel(application) {

    // Added a private member variable to hold a reference to the repository.
    private val repository: WordRepository

    // Using LiveData and caching what getAlphabetizedWords returns has several benefits:
    // - We can put an observer on the data (instead of polling for changes) and only update the
    //   the UI when the data actually changes.
    // - Repository is completely separated from the UI through the ViewModel.
    val allWords: LiveData<List<Word>>

    // Created an init block that gets a reference to the WordDao from the WordRoomDatabase.
    // In the init block, constructed the WordRepository based on the WordRoomDatabase.
    // In the init block, initialized the allWords LiveData using the repository.
    init {
        val wordsDao = WordRoomDatabase.getDatabase(application, viewModelScope).wordDao()
        repository = WordRepository(wordsDao)
        allWords = repository.allWords
    }

    // Created a wrapper insert() method that calls the Repository's insert() method.
    // In this way, the implementation of insert() is encapsulated from the UI.
    // We don't want insert to block the main thread,
    // so we're launching new coroutine and calling the repository's insert,
    // which is a suspend function
    fun insert(word: Word) = viewModelScope.launch(Dispatchers.IO) {
        repository.insert(word)
    }
}