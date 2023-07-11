package com.example.nonono

import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.example.nonono.databinding.ActivityLoginBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class login : AppCompatActivity() {
    lateinit var bind:ActivityLoginBinding
    lateinit var auth:FirebaseAuth
    companion object{
      const  val KEY1 ="com.example.nonono.KEY"
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bind = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(bind.root)
         if(supportActionBar != null){
             supportActionBar!!.hide()
         }
        val dialog = Dialog(this)
        dialog.setContentView(R.layout.dialog)
        auth = FirebaseAuth.getInstance()
       bind.signuplg.setOnClickListener {
            startActivity(Intent(this,signup::class.java))
       }
        bind.checkBox.setOnClickListener {
            bind.checkBox.setTextColor(Color.parseColor("#0000FF"))
        }
       bind.login.setOnClickListener {
           val ema = bind.email.text.toString()
           val pas = bind.pass.text.toString()
           if(ema.isNotEmpty() && pas.isNotEmpty()){
               if(bind.checkBox.isChecked) {
                   dialog.show()
                   auth.signInWithEmailAndPassword(ema, pas).addOnCompleteListener {
                       if (it.isSuccessful) {
                           dialog.dismiss()
                           Toast.makeText(this,"Succes login",Toast.LENGTH_SHORT).show()
                           val intent = Intent(this,ListRecycle::class.java)
                           intent.putExtra(KEY1,pas)
                           startActivity(intent)
                           finish()
                       }
                   }.addOnFailureListener {
                       dialog.dismiss()
                       Toast.makeText(this,"user Does'not Exits",Toast.LENGTH_SHORT).show()
                   }
               }
               else{
                  bind.checkBox.setTextColor(Color.parseColor("#FF0000"))
                   Toast.makeText(this,"Please click the term and condition",Toast.LENGTH_SHORT).show()
               }
           }
           else{
               if(ema.isEmpty() && pas.isNotEmpty()){
                   Toast.makeText(this,"Enter the Email",Toast.LENGTH_SHORT).show()
               }
               else if(ema.isNotEmpty() && pas.isEmpty()){
                   Toast.makeText(this,"Enter the Password",Toast.LENGTH_SHORT).show()
               }
               else{
                   Toast.makeText(this,"Please fill fully",Toast.LENGTH_SHORT).show()
               }
           }
       }
        bind.textView.setOnClickListener {
            startActivity(Intent(this,forget::class.java))
        }

    }
}