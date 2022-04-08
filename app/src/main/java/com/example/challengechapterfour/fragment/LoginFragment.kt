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
import kotlinx.android.synthetic.main.fragment_login.*


class LoginFragment : Fragment() {

    lateinit var prefsLogin : SharedPreferences
    lateinit var prefsTemp : SharedPreferences

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_login, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        prefsTemp = requireContext().getSharedPreferences("SFTEMP", Context.MODE_PRIVATE)
        prefsLogin = requireContext().getSharedPreferences("SF", Context.MODE_PRIVATE)

        if(requireContext().getSharedPreferences("SFTEMP", Context.MODE_PRIVATE).contains("EMAIL") && requireContext().getSharedPreferences("SF", Context.MODE_PRIVATE).contains("USERNAME")){
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
        val dataEmail = et_input_email.text.toString()
        val dataPassword = et_input_password.text.toString()
        val username = prefsLogin.getString("USERNAME", "")

        if(dataEmail == prefsLogin.getString("EMAIL", "") && dataPassword == prefsLogin.getString("PASSWORD", "") && dataEmail.length > 0 && dataPassword.length > 0){
            prefsTemp.edit().putString("PASSWORD", dataPassword).apply()
            prefsTemp.edit().putString("EMAIL", dataEmail).apply()
            Toast.makeText(requireContext(), "Selamat Datang $username", Toast.LENGTH_LONG).show()
            view?.let { Navigation.findNavController(it).navigate(R.id.action_loginFragment_to_homeFragment) }
        }else{
            Toast.makeText(requireContext(), "Username atau Password Masih Salah", Toast.LENGTH_LONG).show()
        }
    }

}