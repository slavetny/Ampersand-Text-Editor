package com.slavetny.ampersand.ui.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.view.View
import android.widget.Toast
import com.slavetny.ampersand.R
import com.slavetny.ampersand.db.Note
import com.slavetny.ampersand.mvp.NotesContract
import com.slavetny.ampersand.mvp.presenter.EditorPresenter
import com.slavetny.ampersand.mvp.presenter.NotesPresenter
import kotlinx.android.synthetic.main.activity_editor.*

class EditorActivity : AppCompatActivity(), NotesContract.NotesView.EditorView, View.OnClickListener {

    private var presenter: EditorPresenter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_editor)

        presenter = EditorPresenter(this, this)

        if (intent.getStringExtra("title") != null) {
            presenter?.getCurrentNote(intent.getStringExtra("title"))

            saveButton.setImageResource(R.drawable.ic_update_48dp)
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

        overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right)
    }
}
