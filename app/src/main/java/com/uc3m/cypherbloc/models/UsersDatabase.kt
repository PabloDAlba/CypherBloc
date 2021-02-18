package com.uc3m.cypherbloc.models

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Users::class], version = 1, exportSchema = false)
abstract class UsersDatabase: RoomDatabase() {

    abstract fun UsersDao(): UsersDao

    companion object{

        @Volatile
        private var INSTANCE: UsersDatabase ?= null

        fun getDatabase(context: Context): UsersDatabase{
            synchronized(this){
                var instance = INSTANCE
                if(instance == null){
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        UsersDatabase::class.java,
                        "user_database")
                        .fallbackToDestructiveMigration().build()
                }
                return instance
            }
        }

    }
}