package com.slavetny.ampersand.ui.activities

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.slavetny.ampersand.NotesContract
import com.slavetny.ampersand.R
import com.slavetny.ampersand.db.Note
import com.slavetny.ampersand.presenter.NotesPresenter
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

            finish()

            overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right)
        }
    }

    override fun showNotes(notesList: List<Note>?) {
        var adapter = NotesAdapter(notesList)

        linearLayoutManager = LinearLayoutManager(this)
        recyclerView.layoutManager = linearLayoutManager
        recyclerView.adapter = adapter

        adapter?.setOnNoteClickedListener(this)
        initRecyclerViewSwipe(adapter, recyclerView, notesList)
    }

    override fun noteClicked(title: String?) {
        startActivity(Intent(this, EditorActivity::class.java).putExtra("title", title))

        overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right)
    }

    private fun initRecyclerViewSwipe(adapter: NotesAdapter, recyclerView: RecyclerView, notesList: List<Note>?) {

        val simpleItemTouchCallback: ItemTouchHelper.SimpleCallback = object :
            ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT or ItemTouchHelper.DOWN or ItemTouchHelper.UP) {

            override fun onMove(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, target: RecyclerView.ViewHolder): Boolean { Toast.makeText(this@NotesActivity, "on Move", Toast.LENGTH_SHORT).show()
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, swipeDir: Int) {
                val position = viewHolder.adapterPosition

                presenter?.removeNote(notesList!!.get(position).title)
            }
        }

        val itemTouchHelper = ItemTouchHelper(simpleItemTouchCallback)
        itemTouchHelper.attachToRecyclerView(recyclerView)
    }

    override fun onResume() {
        super.onResume()

        presenter?.getNotes()
    }
}
