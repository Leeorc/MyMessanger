package com.example.mymessanger


import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import kotlinx.android.synthetic.main.activity_main.*


import android.app.Activity;
import android.view.Menu;

import android.widget.EditText;

import kotlin.math.log

class StateChangeActivity : AppCompatActivity() {


    fun GetTextBox():EditText{
        val textBox: EditText = findViewById(R.id.txtMessage)
        return textBox
    }
    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        val txtBox = GetTextBox()

        outState.putCharSequence("savedText", txtBox.getText())

       // Log.i(TAG, "onSaveInstanceState")
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        val readText = savedInstanceState.getCharSequence("savedText")
        val txtBox = GetTextBox()
        txtBox.setText(readText)
    }
}
