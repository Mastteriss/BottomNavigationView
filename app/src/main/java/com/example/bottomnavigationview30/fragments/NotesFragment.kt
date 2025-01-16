package com.example.bottomnavigationview30.fragments

import android.os.Bundle
import android.text.InputType
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.bottomnavigationview30.databinding.FragmentNotesBinding

import com.example.bottomnavigationviewdz.notes.NotesAdapter

class NotesFragment : Fragment() {
    private var _binding: FragmentNotesBinding? = null
    private val binding get() = _binding!!

    private val notesList = mutableListOf<String>()
    private lateinit var notesAdapter: NotesAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentNotesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        notesAdapter = NotesAdapter(notesList, ::onNoteClick)
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerView.adapter = notesAdapter

        binding.addNoteButton.setOnClickListener {
            showAddNoteDialog()
        }
    }

    private fun showAddNoteDialog() {
        val input = EditText(requireContext())
        input.inputType = InputType.TYPE_CLASS_TEXT
        val layout = LinearLayout(requireContext())
        layout.orientation = LinearLayout.VERTICAL
        layout.addView(input)

        AlertDialog.Builder(requireContext())
            .setTitle("Add Note")
            .setMessage("Enter note:")
            .setView(layout)
            .setPositiveButton("Add") { _, _ ->
                val noteText = input.text.toString()
                if (noteText.isNotBlank()) {
                    notesList.add(noteText)
                    notesAdapter.notifyItemInserted(notesList.size - 1)
                } else {
                    Toast.makeText(requireContext(), "Note cannot be empty", Toast.LENGTH_SHORT).show()
                }
            }
            .setNegativeButton("Cancel", null)
            .show()
    }

    private fun onNoteClick(note: String) {
        showEditNoteDialog(note)
    }

    private fun showEditNoteDialog(note: String) {
        val input = EditText(requireContext())
        input.setText(note)

        AlertDialog.Builder(requireContext())
            .setTitle("Edit Note")
            .setMessage("Edit note:")
            .setView(input)
            .setPositiveButton("Save") { _, _ ->
                val newText = input.text.toString()
                val index = notesList.indexOf(note)
                notesList[index] = newText
                notesAdapter.notifyItemChanged(index)
            }
            .setNegativeButton("Delete") { _, _ ->
                val index = notesList.indexOf(note)
                notesList.removeAt(index)
                notesAdapter.notifyItemRemoved(index)
                Toast.makeText(requireContext(), "Note deleted", Toast.LENGTH_SHORT).show()
            }
            .setNeutralButton("Cancel", null)
            .show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}