package com.slavetny.ampersand.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Note::class], version = 10)
abstract class NoteDatabase : RoomDatabase() {

    abstract fun noteDao(): NoteDao

    companion object {

        private var instance: NoteDatabase? = null

        fun getNoteDatabase(context: Context): NoteDatabase? {

            if (instance == null) {
                instance = Room.databaseBuilder(context.applicationContext, NoteDatabase::class.java, "noteDB")
                    .fallbackToDestructiveMigration()
                    .build()
            }

            return instance
        }

//        fun destroyDataBase(){
//            instance = null
//        }
    }
}