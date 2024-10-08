package com.nixie.projectku

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class LoginActivity : AppCompatActivity() {
    private lateinit var dbHelper: dbhelpersqli
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_login)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val sharedPref = getSharedPreferences("MyPref", Context.MODE_PRIVATE)
        val isLoggedIn = sharedPref.getBoolean("isLoggedIn", false)
        if (isLoggedIn) {
            val intent = Intent(this, DashboardActivity::class.java)
            startActivity(intent)
            finish()
        }
        dbHelper = dbhelpersqli(this)
        val textViewNavigate: TextView = findViewById(R.id.registext)
        textViewNavigate.setOnClickListener {
            val intent = Intent(this, RegisActivity::class.java)
            startActivity(intent)
        }
        val btnsub: Button = findViewById(R.id.btnsubmitlogin)
        val emailtxt: EditText = findViewById(R.id.emailtxt)
        val passwordtxt: EditText = findViewById(R.id.passwordtxt)

        btnsub.setOnClickListener(){
            val email = emailtxt.text.toString()
            val password = passwordtxt.text.toString()

            if (dbHelper.loginUser(email, password)) {

//                intent.putExtra("email", email)

                val editor = sharedPref.edit()
                editor.putString("email_key", email)
                editor.putBoolean("isLoggedIn", true)
                editor.apply()
                val intent = Intent(this, DashboardActivity::class.java)
                startActivity(intent)
                finish()
            } else {
                val builder = AlertDialog.Builder(this)
                builder.setTitle("Login Failed")
                builder.setMessage("Invalid email or password. Please try again.")
                builder.setPositiveButton("OK") { dialog, _ ->
                  dialog.dismiss();
                }
                builder.create().show()
            }
        }

    }
}