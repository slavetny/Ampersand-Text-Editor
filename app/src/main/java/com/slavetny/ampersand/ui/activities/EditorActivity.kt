package com.slavetny.ampersand.ui.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import com.slavetny.ampersand.NotesApp
import com.slavetny.ampersand.R
import com.slavetny.ampersand.db.Note
import com.slavetny.ampersand.NotesContract
import com.slavetny.ampersand.presenter.EditorPresenter
import kotlinx.android.synthetic.main.activity_editor.*

class EditorActivity : AppCompatActivity(), NotesContract.NotesView.EditorView {

    private var presenter: EditorPresenter? = null
    private var notesApp: NotesApp? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_editor)

        notesApp = NotesApp()

        presenter = EditorPresenter(this, this)

        if (intent.getStringExtra("title") != null) {
            presenter?.getCurrentNote(intent.getStringExtra("title")!!)
        }
    }

    override fun loadNote(note: Note) {
        noteTitleField.text = Editable.Factory.getInstance().newEditable(note.title)

        noteField.text = Editable.Factory.getInstance().newEditable(note.text)
    }

    override fun onBackPressed() {
        super.onBackPressed()

        presenter?.saveNote(noteTitleField.text.toString(), noteField.text.toString())

        startActivity(Intent(this, NotesActivity::class.java))

        overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right)
    }
}
