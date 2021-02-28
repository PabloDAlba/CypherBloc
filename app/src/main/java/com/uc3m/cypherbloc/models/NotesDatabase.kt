package com.uc3m.cypherbloc.models

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase


@Database(entities = [Notes::class], version = 1, exportSchema = false)
abstract class NotesDatabase: RoomDatabase() {

    abstract fun NotesDao(): NotesDao

    companion object{

        @Volatile
        private var INSTANCE: NotesDatabase ?= null

        fun getDatabase(context: Context): NotesDatabase{
            synchronized(this){
                var instance = INSTANCE
                if(instance == null){
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        NotesDatabase::class.java,
                        "notes_database")
                        .fallbackToDestructiveMigration().build()
                }
                return instance
            }
        }

    }
}