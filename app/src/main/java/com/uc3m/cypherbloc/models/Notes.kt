package com.uc3m.cypherbloc.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName= "notes_table",primaryKeys = ["title","creator"])
data class Notes(
    val title: String,
    val creator: String,
    val content: String
)
