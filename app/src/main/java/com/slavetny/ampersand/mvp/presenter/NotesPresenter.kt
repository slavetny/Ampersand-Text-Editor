package com.slavetny.ampersand.mvp.presenter

import android.content.Context
import android.util.Log
import com.slavetny.ampersand.db.Note
import com.slavetny.ampersand.mvp.NotesContract
import com.slavetny.ampersand.mvp.NotesModel
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch

class NotesPresenter(val view: NotesContract.NotesView.MenuView, var context: Context) : NotesContract.NotesPresenter.MenuPresenter, NotesContract.NotesModel.OnNotesFinishedListener {

    private var notesModel: NotesContract.NotesModel? = null

    override fun getNotes() {
        notesModel = NotesModel(context)

        notesModel?.getNotesFromDb(this)
    }

    override fun onNotesFinished(notesList: List<Note>?) {
        MainScope().launch {
            view.showNotes(notesList)
        }
    }

    override fun onNotesFailure() {
        Log.d("db error", "empty notes db")
    }
}