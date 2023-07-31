package com.example.todolist

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.todolist.databinding.NoteItemBinding

class NotesAdapter() : RecyclerView.Adapter<NotesAdapter.NotesViewHolder>() {
    var notes:List<Note> = ArrayList<Note>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }
        get():List<Note> {
            return ArrayList<Note>(field)
        }

    var onNoteClickListener: OnNoteClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotesViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.note_item, parent, false)
        return NotesViewHolder(view)
    }

    override fun getItemCount(): Int {
        return notes.size
    }

    override fun onBindViewHolder(viewHolder: NotesViewHolder, position: Int) {
        val note = notes[position]
        viewHolder.bind(note)
    }

    inner class NotesViewHolder(itemView: View) : ViewHolder(itemView) {
        private val binding = NoteItemBinding.bind(itemView)
        fun bind(note: Note) {
            binding.textViewNote.text = note.text
            val colorResId: Int = when (note.priority) {
                0 -> android.R.color.holo_green_light
                1 -> android.R.color.holo_orange_light
                else -> android.R.color.holo_red_light
            }
            val color = ContextCompat.getColor(itemView.context, colorResId)
            binding.textViewNote.setBackgroundColor(color)
            itemView.setOnClickListener {
                onNoteClickListener?.onNoteClick(note)
            }
        }
    }

    interface OnNoteClickListener {
        fun onNoteClick(note: Note)
    }

}