package com.jama.voicetotext


import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentManager


class MainActivity : AppCompatActivity() {
    private var manager: FragmentManager? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        manager = supportFragmentManager
        manager!!.beginTransaction()
            .add(R.id.fr, FirstFragment(), "frag1")
            .commit()
    }
}