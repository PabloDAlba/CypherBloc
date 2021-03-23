package com.uc3m.cypherbloc.models

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface NotesDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addNote(notes: Notes)

    @Query( "SELECT * FROM notes_table")
    fun readAll(): LiveData<List<Notes>>

    @Delete
    suspend fun deleteNote(notes: Notes)


}