package com.nixie.projectku

import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale


class RegisActivity : AppCompatActivity() {
    private lateinit var dbHelper: dbhelpersqli
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_regis)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        dbHelper = dbhelpersqli(this)
        val spinner: Spinner = findViewById(R.id.jk)
        val items = listOf("Laki - Laki", "Perempuan")
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, items)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner.adapter = adapter


        val fullnametext: EditText = findViewById(R.id.fullname)
        val usernametext: EditText = findViewById(R.id.username)
        val emailtext: EditText = findViewById(R.id.email)
        val passwordtext: EditText = findViewById(R.id.password)
        val cpasswordtext: EditText = findViewById(R.id.cpassword)
        val nohptext: EditText = findViewById(R.id.nohp)
        val alamattext: EditText = findViewById(R.id.alamat)
        val submit: Button = findViewById(R.id.btnsubmit)
        val btndate: Button = findViewById(R.id.btndate)
        val textdate: TextView = findViewById(R.id.datetext)

        fullnametext.text = null
        usernametext.text = null
        passwordtext.text = null
        cpasswordtext.text = null
        nohptext.text = null
        alamattext.text = null
        textdate.text = null



        val sdf = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault())
        val currentDate: String = sdf.format(Date())
        textdate.setText(currentDate)

        btndate.setOnClickListener {
            val cd: Calendar = Calendar.getInstance()
            val year = cd.get(Calendar.YEAR)
            val month = cd.get(Calendar.MONTH)
            val day = cd.get(Calendar.DAY_OF_YEAR)

            val datePickerDialog = DatePickerDialog(this, { _, year1, month1, dayOfMonth ->
                val selectedDate = Calendar.getInstance()
                selectedDate.set(year1, month1, dayOfMonth)
                val formattedDate = sdf.format(selectedDate.time)
                textdate.text = formattedDate

            }, year, month, day)

            datePickerDialog.datePicker.maxDate = cd.timeInMillis
            datePickerDialog.show()
        }
        submit.setOnClickListener {
            val fullname = fullnametext.text.toString()
            val username = usernametext.text.toString()
            val email = emailtext.text.toString()
            val password = passwordtext.text.toString()
            val cpassword = cpasswordtext.text.toString()
            val nohp = nohptext.text.toString()
            val alamat = alamattext.text.toString()
            val selectedItem = spinner.selectedItem.toString()
            val date = textdate.text.toString()
            val validationResult = validateForm(
                fullname,
                username,
                email,
                password,
                cpassword,
                nohp,
                alamat,
                selectedItem
            )
            if (validationResult != null) {
                Toast.makeText(this, validationResult, Toast.LENGTH_SHORT).show()
            } else {
                if (validatePasswords(password, cpassword)) {
                    val result = dbHelper.insertUser(
                        fullname,
                        username,
                        date,
                        email,
                        password,
                        nohp.toInt(),
                        alamat,
                        selectedItem
                    )
                    if (result == -1L) {
                        Toast.makeText(this, "Pengguna sudah terdaftar. Silakan gunakan email atau username lain.", Toast.LENGTH_SHORT).show()
                    } else {
                        Toast.makeText(this, "Pendaftaran berhasil!", Toast.LENGTH_SHORT).show()
                        val intent = Intent(this, LoginActivity::class.java)
                        startActivity(intent)
                    }

                } else {
                    Toast.makeText(this, "gagal", Toast.LENGTH_SHORT).show()

                }
            }

        }
    }


    private fun validatePasswords(password: String, confirmPassword: String): Boolean {
        return password == confirmPassword && password.isNotEmpty()
    }

    private fun validateForm(
        fullname: String, username: String, email: String, password: String,
        cpassword: String, nohpText: String, alamat: String, selectedItem: String
    ): String? {
        return when {
            fullname.isEmpty() || username.isEmpty() || email.isEmpty() || password.isEmpty() ||
                    cpassword.isEmpty() || nohpText.isEmpty() || alamat.isEmpty() || selectedItem.isEmpty() -> {
                "semua field harus di isi"
            }

            else -> null
        }
    }
}