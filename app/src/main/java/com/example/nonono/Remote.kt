package com.example.nonono

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.nonono.databinding.ActivityRemoteBinding
import com.google.firebase.messaging.FirebaseMessaging
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import com.google.firebase.remoteconfig.ktx.remoteConfigSettings
import com.squareup.picasso.Picasso

class Remote : AppCompatActivity() {
    lateinit var bind:ActivityRemoteBinding
    lateinit var remoteConfig: FirebaseRemoteConfig
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bind = ActivityRemoteBinding.inflate(layoutInflater)
        setContentView(bind.root)
        if(supportActionBar != null){
            supportActionBar!!.hide()
        }
        FirebaseMessaging.getInstance().subscribeToTopic("Sms")
        remoteConfig = FirebaseRemoteConfig.getInstance()
        val chetime = remoteConfigSettings {
            minimumFetchIntervalInSeconds = 60
        }
     remoteConfig.setConfigSettingsAsync(chetime)
        val im = "https://upload.wikimedia.org/wikipedia/commons/thumb/9/90/Twemoji_1f600.svg/1200px-Twemoji_1f600.svg.png"
        remoteConfig.setDefaultsAsync(mapOf("img" to im))
        val titl = "Hey User"
        remoteConfig.setDefaultsAsync(mapOf("title" to titl))
        val msg = "Hey there is nothing"
        remoteConfig.setDefaultsAsync(mapOf("msse" to msg))
        remoteConfig.fetchAndActivate().addOnCompleteListener (this){tusk->
            if(tusk.isSuccessful){
                val img = remoteConfig.getString("Emoji")
                val tt = remoteConfig.getString("Title")
                val mssg = remoteConfig.getString("massege")
                Picasso.get().load(img).into(bind.emoji)
                bind.Heading.text = tt.toString()
                bind.massege.text = mssg.toString()
            }

        }
    }
}