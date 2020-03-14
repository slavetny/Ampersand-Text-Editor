package com.slavetny.ampersand.mvp

import android.content.Context
import com.slavetny.ampersand.db.Note

interface NotesContract {

    interface NotesModel {

        interface OnNotesFinishedListener {

            fun onNotesFinished(notesList: List<Note>?)

            fun onNotesFailure()

        }

        interface OnNoteFinishedListener {

            fun onNoteFinished(note: Note)

            fun onNotesFailure()

        }

        fun getNotesFromDb(onFinishedListener: OnNotesFinishedListener)

        fun getNoteByTitle(onFinishedListener: OnNoteFinishedListener, title: String)

        fun addNote(title: String, text: String)

    }

    interface NotesPresenter {

        interface MenuPresenter {

            fun getNotes()

        }

        interface EditorPresenter {

            fun getCurrentNote(title: String)

            fun saveNote(title: String, text: String)

        }

    }

    interface NotesView {

        interface MenuView {

            fun showNotes(notesList: List<Note>?)

        }

        interface EditorView {

            fun loadNote(note: Note)

        }

    }
}