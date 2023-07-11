package com.example.nonono

import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.nonono.databinding.ActivityForgetBinding
import com.google.firebase.auth.FirebaseAuth

class forget : AppCompatActivity() {
    lateinit var bind:ActivityForgetBinding
    lateinit var auth:FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bind = ActivityForgetBinding.inflate(layoutInflater)
        setContentView(bind.root)
        auth = FirebaseAuth.getInstance()
        bind.button.setOnClickListener {
            val em = bind.editTextTextEmailAddress.text.toString()
            if (em.isNotEmpty()) {
                auth.sendPasswordResetEmail(em).addOnCompleteListener {
                    if (it.isSuccessful) {
                        val dia = AlertDialog.Builder(this)
                        dia.setTitle("The password reset link send in this Email")
                        dia.setMessage("You can reset your password by this link. Now go to the email and reset the password")
                        dia.setIcon(R.drawable.resetpass)
                        dia.setPositiveButton(
                            "ok",
                            DialogInterface.OnClickListener { dialog, which ->
                                finish()
                            })
                        dia.show()
                    } else {
                        Toast.makeText(this, "You maybe doing wrong", Toast.LENGTH_SHORT).show()
                    }
                }.addOnFailureListener {
                    Toast.makeText(this, "You are offline", Toast.LENGTH_SHORT).show()
                }
            }
            else{
                Toast.makeText(this, "Please Enter The email", Toast.LENGTH_SHORT).show()
            }
        }

    }
}