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
import com.example.challengechapterfour.database.UserDatabase
import kotlinx.android.synthetic.main.fragment_login.*


class LoginFragment : Fragment() {

    private var userDb : UserDatabase? = null

    lateinit var prefs : SharedPreferences

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_login, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        prefs = requireContext().getSharedPreferences("SF", Context.MODE_PRIVATE)


        if(requireContext().getSharedPreferences("SF", Context.MODE_PRIVATE).contains("EMAIL") && requireContext().getSharedPreferences("SF", Context.MODE_PRIVATE).contains("PASSWORD")){
            Navigation.findNavController(view).navigate(R.id.action_loginFragment_to_homeFragment)
        }

        btn_login.setOnClickListener {
            login()
        }

        tv_register.setOnClickListener {
            Navigation.findNavController(view).navigate(R.id.action_loginFragment_to_registerFragment)
        }

    }

    fun login(){


        if(et_input_email.text.isNotEmpty() && et_input_password.text.isNotEmpty()){

            userDb = UserDatabase.getInstance(requireContext())

            val dataEmail = et_input_email.text.toString()
            val dataPassword = et_input_password.text.toString()

            val userCheck = userDb?.UserDao()?.checkLogin(dataEmail, dataPassword)

            if (userCheck.isNullOrEmpty()){
                Toast.makeText(requireContext(), "Email atau Password Masih Salah", Toast.LENGTH_LONG).show()
            }else{
                val username = userDb?.UserDao()?.findUser(dataEmail).toString()

                prefs.edit().putString("PASSWORD", dataPassword).apply()
                prefs.edit().putString("EMAIL", dataEmail).apply()
                prefs.edit().putString("USERNAME", username).apply()

                view?.let { Navigation.findNavController(it).navigate(R.id.action_loginFragment_to_homeFragment) }
            }

        }else{
            Toast.makeText(requireContext(), "Email atau Password Masih Salah", Toast.LENGTH_LONG).show()
        }
    }


}