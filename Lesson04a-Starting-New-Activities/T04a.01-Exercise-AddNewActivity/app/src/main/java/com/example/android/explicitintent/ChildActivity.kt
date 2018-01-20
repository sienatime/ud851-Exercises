package com.example.android.explicitintent

import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.widget.TextView

import kotlinx.android.synthetic.main.activity_child.*

class ChildActivity : AppCompatActivity() {

    private var tvDisplay: TextView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_child)
        tvDisplay = findViewById(R.id.tv_display) as TextView

    }

}
