package com.slavetny.ampersand.ui.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.slavetny.ampersand.R
import com.slavetny.ampersand.db.Note
import com.slavetny.ampersand.mvp.NotesContract
import com.slavetny.ampersand.mvp.presenter.NotesPresenter
import com.slavetny.ampersand.ui.adapters.NotesAdapter
import kotlinx.android.synthetic.main.activity_notes.*

class NotesActivity : AppCompatActivity(), NotesContract.NotesView.MenuView, NotesAdapter.OnNoteClickedListener {

    private var presenter: NotesPresenter? = null
    private lateinit var linearLayoutManager: LinearLayoutManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_notes)

        presenter = NotesPresenter(this, this)
        presenter?.getNotes()

        addNoteButton.setOnClickListener {
            startActivity(Intent(this, EditorActivity::class.java))

            overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right)
        }
    }

    override fun showNotes(notesList: List<Note>?) {
        var adapter = NotesAdapter(notesList)

        linearLayoutManager = LinearLayoutManager(this)
        recyclerView.layoutManager = linearLayoutManager
        recyclerView.adapter = adapter

        adapter.setOnNoteClickedListener(this)
    }

    override fun noteClicked(title: String?) {
        startActivity(Intent(this, EditorActivity::class.java).putExtra("title", title))
    }

    override fun onResume() {
        super.onResume()

        presenter?.getNotes()
    }
}
