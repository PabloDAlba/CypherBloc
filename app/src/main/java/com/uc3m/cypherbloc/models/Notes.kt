package com.uc3m.cypherbloc.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName= "notes_table")
data class Notes(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val title: String,
    val creator: String,
    val content: ByteArray,
    val pass: ByteArray,
    val iv: ByteArray
)
