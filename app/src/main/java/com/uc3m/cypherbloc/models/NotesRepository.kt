package com.uc3m.cypherbloc.models

import androidx.lifecycle.LiveData

class NotesRepository(private val notesDao: NotesDao, email: String) {

    val readAll: LiveData<List<Notes>> = notesDao.readAll(email)

    suspend fun addNote(note : Notes){
        notesDao.addNote(note)
    }

    suspend fun deleteNote(id : Int){
        notesDao.deleteNote(id)
    }

    suspend fun findNote(id: Int) {
        notesDao.findNote(id)
    }

    suspend fun updateNote(id: Int, content: ByteArray, hashPass: ByteArray, iv: ByteArray) {
        notesDao.updateNote(id, content, hashPass, iv)
    }




}