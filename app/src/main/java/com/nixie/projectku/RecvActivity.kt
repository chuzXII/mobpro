package com.nixie.projectku

import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class RecvActivity : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var userAdapter: MyAdapter
    private lateinit var databaseHelper: dbhelpersqli

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_recv)

        // Menambahkan padding untuk tampilan edge-to-edge
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Inisialisasi RecyclerView dan LayoutManager
        recyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)

        // Inisialisasi databaseHelper
        databaseHelper = dbhelpersqli(this)

        // Mendapatkan daftar pengguna dari database
        val userList = databaseHelper.getAllUsers()

        // Inisialisasi adapter dengan data userList
        userAdapter = MyAdapter(userList)
        recyclerView.adapter = userAdapter

        // Menangani event klik pada item di RecyclerView
        userAdapter.setOnItemClickListener(object : MyAdapter.OnItemClickListener {
            override fun onItemClick(position: Int) {
                // Ambil pengguna dari daftar berdasarkan posisi
                val clickedUser = userList[position]
                // Tampilkan Toast dengan nama pengguna
                Toast.makeText(baseContext, "Item clicked: ${clickedUser.fullname}", Toast.LENGTH_SHORT).show()
            }
        })
    }
}
