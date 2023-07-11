package com.example.nonono

import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.nonono.databinding.ActivityNoteShowBinding

class NoteShow : AppCompatActivity() {
    lateinit var bind:ActivityNoteShowBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bind = ActivityNoteShowBinding.inflate(layoutInflater)
        setContentView(bind.root)
        val txtT = intent.getStringExtra("Tut")
        val txtN = intent.getStringExtra("nT")
        val Id = intent.getStringExtra("ItemId")
        bind.showtitle.text = txtT.toString()
        bind.showtext.text = txtN.toString()
        bind.Edit.setOnClickListener {
            val it = Intent(this,EditAc::class.java)
            it.putExtra("title",txtT)
            it.putExtra("Note",txtN)
            it.putExtra("Id",Id)
            val dailog  = AlertDialog.Builder(this)
            dailog.setIcon(R.drawable.question)
            dailog.setTitle("Do You want to Edit this Note?")
            dailog.setMessage("If yes then click Yes")
            dailog.setPositiveButton("Yes",DialogInterface.OnClickListener { dialog, which ->
                startActivity(it)
                finish()
            })
            dailog.setNegativeButton("No",DialogInterface.OnClickListener { dialog, which ->

            })
            dailog.show()
        }
    }
}