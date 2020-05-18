package com.slavetny.ampersand.presenter

import android.content.Context
import android.util.Log
import com.slavetny.ampersand.db.Note
import com.slavetny.ampersand.NotesContract
import com.slavetny.ampersand.NotesModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch

class EditorPresenter(private val view: NotesContract.NotesView.EditorView, var context: Context) : NotesContract.NotesPresenter.EditorPresenter, NotesContract.NotesModel.OnNoteFinishedListener {

    private var notesModel: NotesContract.NotesModel? = null

    override fun getCurrentNote(title: String) {
        notesModel = NotesModel(context)

        notesModel?.initDatabase()

        notesModel?.getNoteByTitle(this, title)
    }

    override fun saveNote(title: String, text: String) {
        notesModel = NotesModel(context)

        notesModel?.initDatabase()

        notesModel?.addNote(title, text)
    }

    @ExperimentalCoroutinesApi
    override fun onNoteFinished(note: Note) {
        MainScope().launch {
            view.loadNote(note)
        }
    }

    override fun onNotesFailure() {
        Log.d("db error", "new note")
    }
}