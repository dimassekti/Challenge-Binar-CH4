package com.example.challengechapterfour

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.custom_dialog_add_note.view.*
import kotlinx.android.synthetic.main.item_adapter_note.view.*

class AdapterNote (val listNote : List<Note>) : RecyclerView.Adapter<AdapterNote.ViewHolder>(){

    var noteDb : NoteDatabase? = null

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdapterNote.ViewHolder {
        val viewItem = LayoutInflater.from(parent.context).inflate(R.layout.item_adapter_note, parent, false)

        return ViewHolder(viewItem)
    }

    override fun onBindViewHolder(holder: AdapterNote.ViewHolder, position: Int) {

        holder.itemView.tv_judul.text = listNote[position].title.toString()
        holder.itemView.tv_content.text = listNote[position].content.toString()


    }

    override fun getItemCount(): Int {
        return listNote.size
    }


}