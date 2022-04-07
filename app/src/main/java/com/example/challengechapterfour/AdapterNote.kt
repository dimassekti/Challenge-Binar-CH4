package com.example.challengechapterfour

import android.app.Activity
import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.custom_dialog_add_note.view.*
import kotlinx.android.synthetic.main.custom_dialog_edit_note.view.*
import kotlinx.android.synthetic.main.item_adapter_note.view.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async

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

        holder.itemView.btn_delete.setOnClickListener {
            noteDb = NoteDatabase.getInstance(it.context)

            val ADBuilder = AlertDialog.Builder(it.context)
                .setTitle("Hapus Data")
                .setMessage("Yakin Hapus?")
                .setPositiveButton("Ya"){ dialogInterface: DialogInterface, i: Int ->
                    GlobalScope.async {
                        val deleteResult = noteDb?.noteDao()?.deleteNote(listNote[position])
                        (holder.itemView.context as HomeFragment).activity?.runOnUiThread {
                            if(deleteResult != null){
                                Toast.makeText(it.context, "Data Berhasil dihapus", Toast.LENGTH_LONG).show()
                                (holder.itemView.context as HomeFragment).activity?.recreate()
                            }else{
                                Toast.makeText(it.context, "Data Gagal dihapus", Toast.LENGTH_LONG).show()
                            }
//                            (holder.itemView.context as HomeFragment).getNoteData()
                        }

                    }
                }
                .setNegativeButton("Tidak"){dialogInterface:DialogInterface, i: Int ->
                    dialogInterface.dismiss()
                }
                ADBuilder.show()

        }


        holder.itemView.btn_update.setOnClickListener {
            noteDb = NoteDatabase.getInstance(it.context)
            val customDialogEdit = LayoutInflater.from(it.context).inflate(R.layout.custom_dialog_edit_note, null, false)

            val ADBuilder = AlertDialog.Builder(it.context)
                .setView(customDialogEdit)
                .create()
            customDialogEdit.btn_update_note.setOnClickListener {

                val title = listNote[position].title
                val content = listNote[position].content

                customDialogEdit.et_update_judul.hint = title
                customDialogEdit.et_update_catatan.hint = content

                if(customDialogEdit.et_update_catatan.text.length > 0 && customDialogEdit.et_update_judul.text.length > 0){

                    val newTitle = customDialogEdit.et_update_judul.text.toString()
                    val newContent = customDialogEdit.et_update_catatan.text.toString()

                    GlobalScope.async {

                        listNote[position].title = newTitle
                        listNote[position].content = newContent
                        val updateNow = noteDb?.noteDao()?.updateNote(listNote[position])

                        (holder.itemView.context as HomeFragment).activity?.runOnUiThread(){
                            if(updateNow != null){
                                Toast.makeText(it.context, "Data Berhasil diupdate", Toast.LENGTH_LONG).show()
                                (holder.itemView.context as HomeFragment).getNoteData()
                            }else{
                                Toast.makeText(it.context, "Data $title Gagal di edit", Toast.LENGTH_SHORT).show()
                            }
//                            (holder.itemView.context as HomeFragment).getNoteData()
                        }
                    }

                    Toast.makeText(it.context, "Data ${title.toString()} Berhasil di edit", Toast.LENGTH_SHORT).show()

                }else{
                    Toast.makeText(it.context, "Mohon input secara lengkap", Toast.LENGTH_LONG).show()
                }
            }
                ADBuilder.show()

        }


    }

    override fun getItemCount(): Int {
        return listNote.size
    }


}