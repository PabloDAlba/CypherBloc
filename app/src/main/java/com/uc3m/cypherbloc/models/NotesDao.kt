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

    @Query( "SELECT * FROM notes_table WHERE creator = :email")
    fun readAll(email: String): LiveData<List<Notes>>

    @Query("DELETE FROM notes_table WHERE id = :id")
    suspend fun deleteNote(id: Int)

    @Query("SELECT * FROM notes_table WHERE id = :id")
    suspend fun findNote(id: Int): Notes

    @Query("UPDATE notes_table SET content = :content, pass = :passHash, iv = :iv  WHERE id = :id")
    suspend fun updateNote(id: Int, content: ByteArray, passHash: ByteArray, iv: ByteArray)



}