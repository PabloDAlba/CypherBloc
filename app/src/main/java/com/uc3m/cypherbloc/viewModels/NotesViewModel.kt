package com.uc3m.cypherbloc.viewModels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.uc3m.cypherbloc.models.Notes
import com.uc3m.cypherbloc.models.NotesDatabase
import com.uc3m.cypherbloc.models.NotesRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class NotesViewModel(application: Application): AndroidViewModel(application) {

    val readAll: LiveData<List<Notes>>
    private val repository: NotesRepository


    init{
        val notesDao = NotesDatabase.getDatabase(application).notesDao()
        repository = NotesRepository(notesDao)
        readAll = repository.readAll
    }

    fun addNote(notes: Notes){
        viewModelScope.launch(Dispatchers.IO) {
            repository.addNote(notes)
        }
    }

    fun deleteNote(notes: Notes) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteNote(notes)
        }
    }


}