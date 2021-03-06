package com.uc3m.cypherbloc.models

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface NotesDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addNote(notes: Notes)

    @Query( "SELECT * FROM notes_table")
    fun readAll(): LiveData<List<Notes>>
}