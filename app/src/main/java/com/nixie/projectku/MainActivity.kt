package com.nixie.projectku
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    private lateinit var dbHelper: dbhelpersqli
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        dbHelper = dbhelpersqli(this)
        val email = intent.getStringExtra("email")
        val cursor = email?.let { dbHelper.getDataByEmail(it) }
        cursor?.use {
            if (it.moveToFirst()) {
                val fullNameIndex = it.getColumnIndex("fullname")
                val usernameIndex = it.getColumnIndex("username")
                val emailIndex = it.getColumnIndex("email")
                val passwordIndex = it.getColumnIndex("password")
                val birthDateIndex = it.getColumnIndex("tgl")
                val genderIndex = it.getColumnIndex("jk")
                val addressIndex = it.getColumnIndex("alamat")
                val phoneNumberIndex = it.getColumnIndex("nohp")
                Log.d("sdd", "fullNameIndex")
                // Check if column index is valid
                if (fullNameIndex != -1) {
                    findViewById<TextView>(R.id.txtfulname).text = it.getString(fullNameIndex)
                }
                if (usernameIndex != -1) {
                    findViewById<TextView>(R.id.txtusername).text = it.getString(usernameIndex)
                }
                if (emailIndex != -1) {
                    findViewById<TextView>(R.id.txtemail).text = it.getString(emailIndex)
                }
                if (passwordIndex != -1) {
                    findViewById<TextView>(R.id.txtpassword).text = it.getString(passwordIndex)
                }
                if (birthDateIndex != -1) {
                    findViewById<TextView>(R.id.txttgllahir).text = it.getString(birthDateIndex)
                }
                if (genderIndex != -1) {
                    findViewById<TextView>(R.id.txtjk).text = it.getString(genderIndex)
                }
                if (addressIndex != -1) {
                    findViewById<TextView>(R.id.txtalamat).text = it.getString(addressIndex)
                }
                if (phoneNumberIndex != -1) {
                    findViewById<TextView>(R.id.txtnohp).text = it.getString(phoneNumberIndex)
                }
            }
        }
    }
}