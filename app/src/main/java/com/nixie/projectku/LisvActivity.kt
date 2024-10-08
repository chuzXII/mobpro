package com.nixie.projectku

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.ListView

import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class LisvActivity : AppCompatActivity() {
    private lateinit var dbHelper: dbhelpersqli
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_lisv)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        dbHelper = dbhelpersqli(this)
        val itemList = dbHelper.getAllUserNames()
        val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, itemList)
        val listView = findViewById<ListView>(R.id.listv)
        listView.adapter = adapter
        listView.setOnItemClickListener { parent, view, position, id ->
            val selectedItem = itemList[position]
            Toast.makeText(this, "Anda memilih: $selectedItem", Toast.LENGTH_SHORT).show()
        }
    }
}