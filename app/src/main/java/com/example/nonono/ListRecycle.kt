package com.example.nonono

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.SearchView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.content.ContextCompat
import androidx.core.graphics.ColorUtils
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.nonono.databinding.ActivityListRecycleBinding
import com.google.android.material.internal.ViewUtils.getBackgroundColor
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.messaging.FirebaseMessaging

class ListRecycle : AppCompatActivity() {
    lateinit var bind : ActivityListRecycleBinding
    lateinit var recyclerView: RecyclerView
    lateinit var searchView: SearchView
    lateinit var adapter: myAdapter
    private val filterredList = mutableListOf<users>()
    private val todoTitlearray = mutableListOf<users>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bind = ActivityListRecycleBinding.inflate(layoutInflater)
        setContentView(bind.root)
        if(supportActionBar != null) {
            supportActionBar!!.hide()
        }
        // val pa = intent.getStringExtra(login.KEY1)
        bind.floatingActionButton.setOnClickListener {
            Toast.makeText(this,"Please Write something" , Toast.LENGTH_SHORT).show()
            startActivity(Intent(this,addNote::class.java))
        }
        recyclerView = findViewById(R.id.recyle)
        searchView = findViewById(R.id.Se)
        val database = FirebaseDatabase.getInstance()
        val user = FirebaseAuth.getInstance().currentUser
        val userId = user?.uid
        val reference = database.getReference("users").child(userId.toString()).child("todos")
        reference.addValueEventListener(object :ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                todoTitlearray.clear()
                for(childSnapspot in snapshot.children){
                    val itemId = childSnapspot.key.toString()
                    val todoItemTitle = childSnapspot.child("Title").getValue(String::class.java)
                    val todoNote = childSnapspot.child("note").getValue(String::class.java)
                    todoItemTitle?.let {
                        val use = users(itemId,todoItemTitle,todoNote)
                        todoTitlearray.add(use)
                    }
                }
                adapter.notifyDataSetChanged()
            }
            override fun onCancelled(error: DatabaseError) {
                //Give the logcat for show the error
            }

        })
        recyclerView.layoutManager = LinearLayoutManager(this)
        adapter = myAdapter(todoTitlearray ,this)
        recyclerView.adapter = adapter
        adapter.setItemclickListener(object :myAdapter.onItemclickListener{
            override fun onItemclick(position: Int) {
                val intent = Intent(this@ListRecycle,NoteShow::class.java)
                intent.putExtra("Tut",todoTitlearray[position].tit)
                intent.putExtra("nT",todoTitlearray[position].not)
                intent.putExtra("ItemId",todoTitlearray[position].itemId)
                startActivity(intent)
            }

        })


    }



}