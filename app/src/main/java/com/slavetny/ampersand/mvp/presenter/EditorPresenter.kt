package com.slavetny.ampersand.mvp.presenter

import android.content.Context
import android.util.Log
import com.slavetny.ampersand.db.Note
import com.slavetny.ampersand.mvp.NotesContract
import com.slavetny.ampersand.mvp.NotesModel
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch

class EditorPresenter(val view: NotesContract.NotesView.EditorView, var context: Context) : NotesContract.NotesPresenter.EditorPresenter, NotesContract.NotesModel.OnNoteFinishedListener {

    private var notesModel: NotesContract.NotesModel? = null

    override fun getCurrentNote(title: String) {
        notesModel = NotesModel(context)

        notesModel?.getNoteByTitle(this, title)
    }

    override fun saveNote(title: String, text: String) {
        notesModel = NotesModel(context)

        notesModel?.addNote(title, text)
    }

    override fun onNoteFinished(note: Note) {
        MainScope().launch {
            view?.loadNote(note)
        }
    }

    override fun onNotesFailure() {
        Log.d("db error", "new note")
    }
}