package com.example.todolist

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.todolist.databinding.ActivityAddNoteBinding

class AddNoteActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddNoteBinding
    private lateinit var viewModel: AddNoteViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddNoteBinding.inflate(layoutInflater)
        setContentView(binding.root)
        viewModel = ViewModelProvider(this)[AddNoteViewModel::class.java]
        viewModel.getShouldCloseScreen().observe(this) { shouldClose ->
            if (shouldClose) finish()
        }
        binding.buttonSave.setOnClickListener {
            saveNote()
        }
    }

    private fun saveNote() {
        val text = binding.editTextNote.text.trim().toString()
        if (text.isEmpty()) {
            Toast.makeText(this, "Заметка пустая!Заполни поле текста.", Toast.LENGTH_SHORT).show()
            return
        }
        val priority = getPriority()
        val note = Note(text, priority)
        viewModel.saveNote(note)

    }

    private fun getPriority(): Int {
        val priority = if (binding.radioButtonLow.isChecked) {
            0
        } else if (binding.radioButtonMedium.isChecked) {
            1
        } else 2
        return priority
    }

    companion object {
        fun newIntent(context: Context): Intent {
            return Intent(context, AddNoteActivity::class.java)
        }
    }

}