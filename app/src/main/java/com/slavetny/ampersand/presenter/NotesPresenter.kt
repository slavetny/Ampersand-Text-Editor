package com.slavetny.ampersand.presenter

import android.content.Context
import android.util.Log
import com.slavetny.ampersand.db.Note
import com.slavetny.ampersand.NotesContract
import com.slavetny.ampersand.NotesModel
import kotlinx.coroutines.*

class NotesPresenter(private val view: NotesContract.NotesView.MenuView, var context: Context) : NotesContract.NotesPresenter.MenuPresenter, NotesContract.NotesModel.OnNotesFinishedListener {

    private var notesModel: NotesContract.NotesModel? = null

    override fun getNotes() {
        notesModel = NotesModel(context)

        notesModel?.initDatabase()

        notesModel?.getNotesFromDb(this)
    }

    override fun removeNote(title: String) {
        notesModel = NotesModel(context)

        notesModel?.initDatabase()

        notesModel?.deleteNote(title)
    }

    @ExperimentalCoroutinesApi
    override fun onNotesFinished(notesList: List<Note>?) {
        MainScope().launch {
            view.showNotes(notesList)
        }
    }

    override fun onNotesFailure() {
        Log.d("db error", "empty notes db")
    }
}