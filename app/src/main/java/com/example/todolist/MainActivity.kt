package com.example.todolist

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.todolist.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var notesAdapter: NotesAdapter
    private lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        viewModel = ViewModelProvider(this)[MainViewModel::class.java]

        notesAdapter = NotesAdapter()
        binding.rVNotes.adapter = notesAdapter
        viewModel.getNotes().observe(this) { notes -> notesAdapter.notes = notes }

        val itemTouchHelper = ItemTouchHelper(object : ItemTouchHelper.
        SimpleCallback(
            0,
            ItemTouchHelper.RIGHT
        ) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.adapterPosition
                val note = notesAdapter.notes[position]
                viewModel.remove(note)
            }
        })
        itemTouchHelper.attachToRecyclerView(binding.rVNotes)

        binding.buttonAddNote.setOnClickListener {
            val intent = AddNoteActivity.newIntent(this@MainActivity)
            startActivity(intent)
        }
    }

}
