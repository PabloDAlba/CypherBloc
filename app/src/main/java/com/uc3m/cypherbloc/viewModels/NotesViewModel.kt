package com.uc3m.cypherbloc.viewModels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.uc3m.cypherbloc.models.Notes
import com.uc3m.cypherbloc.models.NotesDatabase
import com.uc3m.cypherbloc.models.NotesRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class NotesViewModel(application: Application): AndroidViewModel(application) {

    val readAll: LiveData<List<Notes>>
    private val repository: NotesRepository
    private val emailUser :String = Firebase.auth.currentUser.email.toString()


    init{
        val notesDao = NotesDatabase.getDatabase(application).notesDao()
        repository = NotesRepository(notesDao, emailUser)
        readAll = repository.readAll
    }

    fun addNote(notes: Notes){
        viewModelScope.launch(Dispatchers.IO) {
            repository.addNote(notes)
        }
    }

    fun deleteNote(id: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteNote(id)
        }
    }

    fun findNote(id: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.findNote(id)
        }
    }

    fun updateNote(id: Int, content: ByteArray) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.updateNote(id, content)
        }
    }


}