package com.uc3m.cypherbloc.models

import android.provider.ContactsContract
import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface NotesDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addNote(notes: Notes)

    @Query( "SELECT * FROM notes_table WHERE creator = :email")
    fun readAll(email: String): LiveData<List<Notes>>

    @Query("DELETE FROM notes_table WHERE id = :id")
    suspend fun deleteNote(id: Int)

    @Query("SELECT * FROM notes_table WHERE id = :id")
    suspend fun findNote(id: Int): Notes

    @Query("UPDATE notes_table SET content = :content WHERE id = :id")
    suspend fun updateNote(id: Int, content: ByteArray)



}