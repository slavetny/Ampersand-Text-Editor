package com.slavetny.ampersand.ui.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.view.View
import android.widget.Toast
import com.slavetny.ampersand.NotesApp
import com.slavetny.ampersand.R
import com.slavetny.ampersand.db.Note
import com.slavetny.ampersand.NotesContract
import com.slavetny.ampersand.presenter.EditorPresenter
import kotlinx.android.synthetic.main.activity_editor.*

class EditorActivity : AppCompatActivity(), NotesContract.NotesView.EditorView, View.OnClickListener {

    private var presenter: EditorPresenter? = null
    private var notesApp: NotesApp? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        notesApp = NotesApp()

        if (notesApp?.get()?.getTheme(this) == true)
            setTheme(R.style.DarkTheme)
        else
            setTheme(R.style.WhiteTheme)

        setContentView(R.layout.activity_editor)

        presenter = EditorPresenter(this, this)

        if (intent.getStringExtra("title") != null) {
            presenter?.getCurrentNote(intent.getStringExtra("title"))
        }

        saveButton.setOnClickListener(this)
    }

    override fun loadNote(note: Note) {
        noteTitleField.text = Editable.Factory.getInstance().newEditable(note.title)

        noteField.text = Editable.Factory.getInstance().newEditable(note.text)
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.saveButton -> {
                presenter?.saveNote(noteTitleField.text.toString(), noteField.text.toString())

                Toast.makeText(this, "Note was saved...", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()

        startActivity(Intent(this, NotesActivity::class.java))

        overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right)
    }
}
