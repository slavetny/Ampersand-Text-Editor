package com.slavetny.ampersand.mvp

import android.content.Context
import androidx.annotation.UiThread
import com.slavetny.ampersand.db.Note
import com.slavetny.ampersand.db.NoteDao
import com.slavetny.ampersand.db.NoteDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class NotesModel(val context: Context) : NotesContract.NotesModel {

    private var database: NoteDatabase? = null
    private var noteDao: NoteDao? = null
    private val viewModelScope = CoroutineScope(Dispatchers.IO)

    override fun getNotesFromDb(onFinishedListener: NotesContract.NotesModel.OnNotesFinishedListener) {
        viewModelScope.launch {
            getAllNotesFromDb(onFinishedListener)
        }
    }

    override fun getNoteByTitle(onFinishedListener: NotesContract.NotesModel.OnNoteFinishedListener, title: String) {
        viewModelScope.launch {
            getNoteByTitleFromDb(onFinishedListener, title)
        }
    }

    override fun addNote(title: String, text: String) {
        viewModelScope.launch {
            addNoteToDb(title, text)
        }
    }

    @UiThread
    fun getAllNotesFromDb(onFinishedListener: NotesContract.NotesModel.OnNotesFinishedListener) {
        database = NoteDatabase.getNoteDatabase(context)

        noteDao = database?.noteDao()

        onFinishedListener.onNotesFinished(noteDao?.getNotes())

        onFinishedListener.onNotesFailure()
    }

    @UiThread
    fun getNoteByTitleFromDb(onFinishedListener: NotesContract.NotesModel.OnNoteFinishedListener, title: String) {
        database = NoteDatabase.getNoteDatabase(context)

        noteDao = database?.noteDao()

        onFinishedListener.onNoteFinished(noteDao!!.getNoteByTitle(title))

        onFinishedListener.onNotesFailure()
    }

    @UiThread
    fun addNoteToDb(title: String, text: String) {
        database = NoteDatabase.getNoteDatabase(context)

        noteDao = database?.noteDao()

        var note = Note(title, text)

        noteDao?.insert(note)
    }
}