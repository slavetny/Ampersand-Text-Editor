package com.slavetny.ampersand

import android.content.Context
import com.slavetny.ampersand.db.Note
import com.slavetny.ampersand.db.NoteDao
import com.slavetny.ampersand.db.NoteDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class NotesModel(private val context: Context) : NotesContract.NotesModel {

    private var database: NoteDatabase? = null
    private var noteDao: NoteDao? = null
    private val viewModelScope = CoroutineScope(Dispatchers.IO)

    override fun initDatabase() {
        database = NoteDatabase.getNoteDatabase(context)

        noteDao = database?.noteDao()
    }

    override fun getNotesFromDb(onFinishedListener: NotesContract.NotesModel.OnNotesFinishedListener) {
        viewModelScope.launch {
            onFinishedListener.onNotesFinished(noteDao?.getNotes())

            onFinishedListener.onNotesFailure()
        }
    }

    override fun getNoteByTitle(onFinishedListener: NotesContract.NotesModel.OnNoteFinishedListener, title: String) {
        viewModelScope.launch {
            onFinishedListener.onNoteFinished(noteDao!!.getNoteByTitle(title))

            onFinishedListener.onNotesFailure()
        }
    }

    override fun addNote(title: String, text: String) {
        viewModelScope.launch {
            val note = Note(title, text)

            noteDao?.insert(note)
        }
    }

    override fun deleteNote(title: String) {
        viewModelScope.launch {
            val note = noteDao!!.getNoteByTitle(title)

            try {
                noteDao?.delete(note)
            } catch (e: NullPointerException) {
                e.fillInStackTrace()
            }
        }
    }
}