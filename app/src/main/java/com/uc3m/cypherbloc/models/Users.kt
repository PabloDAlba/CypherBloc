package com.uc3m.cypherbloc.models

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "users_table")
data class Users(
        @PrimaryKey
        val email: String,
        val password: String,
        val Username: String,
)
