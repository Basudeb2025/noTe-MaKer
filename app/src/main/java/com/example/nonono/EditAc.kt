package com.example.nonono

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.nonono.databinding.ActivityEditBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class EditAc : AppCompatActivity() {
    lateinit var bind : ActivityEditBinding
    lateinit var auth : FirebaseAuth
    lateinit var databaseReference: DatabaseReference
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bind = ActivityEditBinding.inflate(layoutInflater)
        setContentView(bind.root)
        if(supportActionBar != null){
            supportActionBar!!.hide()
        }
        val txt = intent.getStringExtra("Note")
        auth = FirebaseAuth.getInstance()
        bind.edttxt.setText(txt.toString())
        val title = intent.getStringExtra("title")
        val id = intent.getStringExtra("Id")
        bind.showtitle.text = title.toString()
        val userId = auth.currentUser!!.uid
        databaseReference = FirebaseDatabase.getInstance().getReference("users")
        val reference = databaseReference.child(userId).child("todos")
        bind.Edit.setOnClickListener {
            val updateData = bind.edttxt.text.toString()
            reference.child(id.toString()).child("note").setValue(updateData).addOnCompleteListener {
                Toast.makeText(this,"Data Update Succesful",Toast.LENGTH_SHORT).show()
                startActivity(Intent(this,ListRecycle::class.java))
                finish()
            }.addOnFailureListener {
                Toast.makeText(this,"Failed",Toast.LENGTH_SHORT).show()
            }
        }

    }
}