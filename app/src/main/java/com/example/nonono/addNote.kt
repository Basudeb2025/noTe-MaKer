package com.example.nonono

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.nonono.databinding.ActivityAddNoteBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class addNote : AppCompatActivity() {
    lateinit var bind:ActivityAddNoteBinding
    lateinit var auth:FirebaseAuth
    lateinit var databaseReference: DatabaseReference
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bind = ActivityAddNoteBinding.inflate(layoutInflater)
        setContentView(bind.root)
        if(supportActionBar != null){
            supportActionBar!!.hide()
        }
        auth = FirebaseAuth.getInstance()
        bind.sbt.setOnClickListener {
            val Tytle = bind.Title.text.toString()
            val txt = bind.noteTxt.text.toString()
            if(Tytle.isNotEmpty() && txt.isNotEmpty()){
                val user = FirebaseAuth.getInstance().currentUser
                val userId = user?.uid
                val reference = FirebaseDatabase.getInstance().getReference("users").child(userId.toString()).child("todos")
               val newTodoReference = reference.push() // this is for create the uniqe id
                val pushId = newTodoReference.key
                val em = auth.currentUser?.email
                val mp = mapOf("note" to txt, "Title" to Tytle ,"PushId" to pushId, "Email" to em)
                newTodoReference.setValue(mp)
                startActivity(Intent(this,ListRecycle::class.java))
                finish()
            }
            else if(Tytle.isEmpty() && txt.isNotEmpty()){
                Toast.makeText(this,"Please give the Note title",Toast.LENGTH_SHORT).show()
            }
            else if(Tytle.isNotEmpty() && txt.isEmpty()){
                Toast.makeText(this,"Enter something on Note",Toast.LENGTH_SHORT).show()
            }
            else{
                Toast.makeText(this,"Fill Fully",Toast.LENGTH_SHORT).show()
            }
        }
    }
}