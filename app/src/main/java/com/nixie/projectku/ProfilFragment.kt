package com.nixie.projectku

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [ProfilFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ProfilFragment : Fragment() {
    private lateinit var dbHelper: dbhelpersqli

    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_profil, container, false)
        val btnlogout = view.findViewById<Button>(R.id.button_logout)
        btnlogout.setOnClickListener {
            val sharedPreferences =
                requireActivity().getSharedPreferences("MyPref", Context.MODE_PRIVATE)
            val editor = sharedPreferences.edit()
            editor.clear()
            editor.apply()
            val i = Intent(requireContext(), LoginActivity::class.java)
            startActivity(i)
        }
        val sharedPref = requireActivity().getSharedPreferences("MyPref", Context.MODE_PRIVATE)
        val email = sharedPref.getString("email_key", null)
        Log.d("sss", "email : "+email)
        val databaseHelper = dbhelpersqli(requireContext())
        val cursor = email?.let { databaseHelper.getDataByEmail(it) }

        // Ambil referensi TextView tanpa langsung mengatur teksnya
        val fullNameTextView = view.findViewById<TextView>(R.id.txtfulname)
        val usernameTextView = view.findViewById<TextView>(R.id.txtusername)
        val emailTextView = view.findViewById<TextView>(R.id.txtemail)
        val passwordTextView = view.findViewById<TextView>(R.id.txtpassword)
        val birthDateTextView = view.findViewById<TextView>(R.id.txttgllahir)
        val genderTextView = view.findViewById<TextView>(R.id.txtjk)
        val addressTextView = view.findViewById<TextView>(R.id.txtalamat)
        val phoneNumberTextView = view.findViewById<TextView>(R.id.txtnohp)

        // Gunakan `cursor` untuk mengambil data dari SQLite
        cursor?.use {
            if (it.moveToFirst()) {
                val fullName = it.getString(it.getColumnIndexOrThrow("fullname"))
                val username = it.getString(it.getColumnIndexOrThrow("username"))
                val emailValue = it.getString(it.getColumnIndexOrThrow("email"))
                val password = it.getString(it.getColumnIndexOrThrow("password"))
                val birthDate = it.getString(it.getColumnIndexOrThrow("tgl"))
                val gender = it.getString(it.getColumnIndexOrThrow("jk"))
                val address = it.getString(it.getColumnIndexOrThrow("alamat"))
                val phoneNumber = it.getString(it.getColumnIndexOrThrow("nohp"))
                Log.d("sd", fullName)
                // Atur teks TextView
                fullNameTextView.text = fullName
                usernameTextView.text = username
                emailTextView.text = emailValue
                passwordTextView.text = password
                birthDateTextView.text = birthDate
                genderTextView.text = gender
                addressTextView.text = address
                phoneNumberTextView.text = phoneNumber
            }
        }
        return view;


    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment ProfilFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            ProfilFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
    private fun loadDataFromDatabase() {

    }
}