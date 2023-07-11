package com.example.nonono

import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.CheckBox
import android.widget.Toast
import com.example.nonono.databinding.ActivitySignupBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class signup : AppCompatActivity() {
    lateinit var bind:ActivitySignupBinding
    lateinit var auth:FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bind = ActivitySignupBinding.inflate(layoutInflater)
        setContentView(bind.root)
        if(supportActionBar!=null){
            supportActionBar!!.hide()
        }
        auth = FirebaseAuth.getInstance()
        bind.signup.setOnClickListener {
            val name = bind.signname.text.toString()
            val em = bind.signem.text.toString()
            val pa1 = bind.signpas1.text.toString()
            val pa2 = bind.signpas2.text.toString()
            val dialog = Dialog(this)
            dialog.setContentView(R.layout.disclaimer)
            val btnok =dialog.findViewById<Button>(R.id.button2)
            val chbx = dialog.findViewById<CheckBox>(R.id.checkBox2)
            val mp = mapOf("name" to name,"email" to em,"password1" to pa1 , "password2" to pa2)
            if(name.isNotEmpty() && em.isNotEmpty() && pa1.isNotEmpty() && pa2.isNotEmpty()){
               if(pa1 != pa2){
                   Toast.makeText(this,"Please Enter same password",Toast.LENGTH_SHORT).show()
               }
                else if(pa1 == pa2){
                   dialog.show()
                   btnok.setOnClickListener {
                       if (chbx.isChecked) {
                           auth.createUserWithEmailAndPassword(em, pa1).addOnCompleteListener {
                               if (it.isSuccessful) {
                                   startActivity(Intent(this,login::class.java))
                                   finish()
                               }
                               else{
                                   dialog.dismiss()
                                   Toast.makeText(this, "You are doing something Wrong", Toast.LENGTH_SHORT
                                   ).show()
                               }
                           }.addOnCanceledListener {
                               dialog.dismiss()
                               Toast.makeText(this, "Please check your internet", Toast.LENGTH_SHORT
                               ).show()
                           }
                       }
                       else{
                           chbx.setTextColor(Color.parseColor("#ff0000"))
                           Toast.makeText(this,"Please click at Check-box",Toast.LENGTH_SHORT).show()
                       }
                   }
               }
            }
            else{
                Toast.makeText(this,"Please enter fully",Toast.LENGTH_SHORT).show()
            }
        }
    }
}