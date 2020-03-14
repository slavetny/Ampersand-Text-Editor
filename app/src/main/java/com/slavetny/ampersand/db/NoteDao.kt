package com.slavetny.ampersand.db

import androidx.room.*

@Dao
interface NoteDao {

    @Query("Select * From note")
    fun getNotes(): List<Note>

    @Query("Select * From note Where title == :title")
    fun getNoteByTitle(title: String): Note

    @Insert
    fun insert(note: Note)

    @Update
    fun update(note: Note)

    @Delete
    fun delete(note: Note)

}