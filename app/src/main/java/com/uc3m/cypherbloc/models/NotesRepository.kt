package com.uc3m.cypherbloc.models

import androidx.lifecycle.LiveData

class NotesRepository(private val notesDao: NotesDao) {

    val readAll: LiveData<List<Notes>> = notesDao.readAll()

    suspend fun addNote(note : Notes){
        notesDao.addNote(note)
    }

    suspend fun deleteNote(note : Notes){
        notesDao.deleteNote(note)
    }




}