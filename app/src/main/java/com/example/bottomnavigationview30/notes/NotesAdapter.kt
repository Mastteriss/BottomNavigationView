package com.example.bottomnavigationviewdz.notes

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.bottomnavigationview30.R


class NotesAdapter(
    private val notesList: MutableList<String>,
    private val onNoteClick: (String) -> Unit
) : RecyclerView.Adapter<NotesAdapter.ViewHolder>() {

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val noteTextView: TextView = itemView.findViewById(R.id.noteTextView)
        val checkBox: CheckBox = itemView.findViewById(R.id.checkbox)

        init {
            itemView.setOnClickListener {
                onNoteClick(notesList[adapterPosition])
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_note, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val note = notesList[position]
        holder.noteTextView.text = note
        holder.checkBox.setOnCheckedChangeListener(null)
        holder.checkBox.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                holder.noteTextView.paint.isStrikeThruText = true
            } else {
                holder.noteTextView.paint.isStrikeThruText = false
            }
        }
    }

    override fun getItemCount(): Int {
        return notesList.size
    }
}