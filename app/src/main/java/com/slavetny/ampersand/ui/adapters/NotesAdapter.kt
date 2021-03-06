package com.slavetny.ampersand.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.slavetny.ampersand.R
import com.slavetny.ampersand.db.Note
import com.slavetny.ampersand.utils.extensions.inflate
import kotlinx.android.synthetic.main.note_item.view.*

class NotesAdapter(var notesList: List<Note>?) : RecyclerView.Adapter<NotesAdapter.NotesViewHolder>() {

    private var onNoteClickedListener: OnNoteClickedListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        NotesViewHolder (
            parent.inflate(R.layout.note_item)
        )

    override fun getItemCount() = notesList!!.size

    override fun onBindViewHolder(holder: NotesViewHolder, position: Int) {
        holder.bind(notesList!!.get(position))

        holder.itemView.setOnClickListener {
            onNoteClickedListener?.noteClicked(notesList?.get(position)?.title)
        }
    }

    fun setOnNoteClickedListener(onNoteClickedListener: OnNoteClickedListener) {
        this.onNoteClickedListener = onNoteClickedListener
    }

    class NotesViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(note: Note) {
            itemView.noteTitle.text = note.title
            itemView.noteText.text = note.text
        }
    }

    interface OnNoteClickedListener {
        fun noteClicked(title: String?)
    }
}