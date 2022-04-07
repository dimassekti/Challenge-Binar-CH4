package com.example.challengechapterfour

import android.app.AlertDialog
import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.custom_dialog_add_note.*
import kotlinx.android.synthetic.main.custom_dialog_add_note.view.*
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch


class HomeFragment : Fragment() {

    lateinit var prefsHome : SharedPreferences
    lateinit var prefsTemp : SharedPreferences
    var noteDb : NoteDatabase? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        prefsHome = requireContext().getSharedPreferences("SF", Context.MODE_PRIVATE)
        prefsTemp = requireContext().getSharedPreferences("SFTEMP", Context.MODE_PRIVATE)

        noteDb = NoteDatabase.getInstance(requireContext())

        getNoteData()

        val usernameIn = prefsHome.getString("USERNAME", "")
        val emailIn = prefsHome.getString("EMAIL", "")
        val passwordIn = prefsHome.getString("PASSWORD", "")

        tv_header.setText("Hello $usernameIn")

        tv_logout.setOnClickListener {
            prefsHome.edit().clear().apply()
            prefsTemp.edit().clear().apply()
            Navigation.findNavController(view).navigate(R.id.action_homeFragment_to_loginFragment)
        }

            fab_add.setOnClickListener {

                val customDialogAdd = LayoutInflater.from(requireContext()).inflate(R.layout.custom_dialog_add_note, null, false)

                val ADBuilder = AlertDialog.Builder(requireContext())
                    .setView(customDialogAdd)
                    .create()
                customDialogAdd.btn_input_note.setOnClickListener {

                        if(customDialogAdd.et_input_judul.text.length > 0 && customDialogAdd.et_input_catatan.text.length > 0){

                            GlobalScope.async {

                                val judul = customDialogAdd.et_input_judul.text.toString()
                                val catatan = customDialogAdd.et_input_catatan.text.toString()
                                val data = noteDb?.noteDao()?.insertNote(Note(null, judul, catatan))

                             }

                            Toast.makeText(requireContext(), "Data Berhasil di Input", Toast.LENGTH_LONG).show()
                            ADBuilder.dismiss()

                        }else{
                            Toast.makeText(requireContext(), "Mohon input secara lengkap", Toast.LENGTH_LONG).show()
                        }


                }
                ADBuilder.show()

            }
    }



    fun getNoteData(){
        rv_note.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)

        GlobalScope.launch {
            val listData = noteDb?.noteDao()?.getAllNote()
            activity?.runOnUiThread {
                listData.let {
                    rv_note.adapter = AdapterNote(it!!)
                }
            }
        }
    }



    override fun onResume() {
        super.onResume()
        getNoteData()
    }

    override fun onDestroy() {
        super.onDestroy()
        NoteDatabase.destroyInstance()
    }


}