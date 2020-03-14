package com.slavetny.ampersand.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

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

//        private val MIGRATION_1_2 = object : Migration(1, 2) {
//            override fun migrate(database: SupportSQLiteDatabase) {
//                database.execSQL("ALTER TABLE 'note' ADD COLUMN 'text' STRING NOT NULL");
//            }
//        }

        fun destroyDataBase(){
            instance = null
        }
    }
}