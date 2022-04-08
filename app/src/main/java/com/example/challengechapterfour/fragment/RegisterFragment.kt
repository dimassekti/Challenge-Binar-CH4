package com.example.challengechapterfour.fragment

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.Navigation
import com.example.challengechapterfour.R
import com.example.challengechapterfour.database.NoteDatabase
import com.example.challengechapterfour.user.User
import kotlinx.android.synthetic.main.fragment_register.*


class RegisterFragment : Fragment() {

    lateinit var prefsRegister : SharedPreferences
//    var noteDb : NoteDatabase? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_register, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //db
//        noteDb = NoteDatabase.getInstance(requireContext())

        //sharedpreferences
        prefsRegister = requireContext().getSharedPreferences("SF", Context.MODE_PRIVATE)

        btn_register.setOnClickListener {
            val username = et_username.text.toString()
            val email = et_email.text.toString()
            val password = et_password.text.toString()
            val passwordConf = et_password_confirm.text.toString()

//            var checkUsername = noteDb?.userDao()?.findUser(username)

            if(et_username.text.length > 0
                && et_email.text.length > 0
                && et_password.text.length > 0
                && et_password_confirm.text.length > 0){

                    if(et_password.text == et_password_confirm.text){
                        prefsRegister.edit().putString("USERNAME", username).apply()
                        prefsRegister.edit().putString("EMAIL", email).apply()
                        prefsRegister.edit().putString("PASSWORD", password).apply()
                        prefsRegister.edit().putString("PASSWORDCONF", passwordConf).apply()

//                noteDb?.userDao()?.insertUser(User(null, username, email, password))
                        Toast.makeText(requireContext(), "Register Berhasil", Toast.LENGTH_LONG).show()
                        Navigation.findNavController(view).navigate(R.id.action_registerFragment_to_loginFragment2)
                    }else{
                        Toast.makeText(requireContext(), "Password tidak sama", Toast.LENGTH_LONG).show()
                    }
            }
            else{
                Toast.makeText(requireContext(), "Mohon isi semua data dengan lengkap", Toast.LENGTH_LONG).show()
            }


        }

        tv_login.setOnClickListener {
            Navigation.findNavController(view).navigate(R.id.action_registerFragment_to_loginFragment2)
        }

    }

}