package com.example.mymessanger
import android.os.Bundle
import android.os.StrictMode
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import java.io.IOException
import java.net.URL
import java.io.File
import android.os.Environment
import android.util.Log
import 	androidx.core.content.ContextCompat
import android.content.pm.PackageManager
import android.Manifest;
import androidx.core.app.ActivityCompat

class MainActivity : AppCompatActivity() {
    private val TAG = "MyLogs"
    val REQUEST_PERMISSION = 123
    val path = File(Environment.getExternalStorageDirectory(),"/MyMessanger/")
    private val LAST_SEND_TEXT = "sendText.txt"
    private val LAST_IP = "ip.txt"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        println("Start XXX")
        Log.i(TAG, "Start GGG")
        val lastMessage = ReadFile(LAST_SEND_TEXT) //savedInstanceState.getCharSequence("savedText")
        var lastIp  =  ReadFile(LAST_IP)
        if(lastMessage!="")
           GetMessageTextBox().setText(lastMessage)
        if(lastIp != "")
            GetIpTextBox().setText(lastIp)



        val policy = StrictMode.ThreadPolicy.Builder().permitAll().build()
        StrictMode.setThreadPolicy(policy)
      //  writeToFile()
        val btnSend = findViewById(R.id.btnSend) as Button
        btnSend.setOnClickListener() {

            SendRequest()


        }
    }

    fun SendRequest() {


        // Toast.makeText(this,"Leora clicked", Toast.LENGTH_SHORT).show()
        Toast.makeText(this, "Leora clicked", Toast.LENGTH_SHORT).show()
        val url = "http://${GetIpTextBox().getText()}/?data=${GetMessageTextBox().getText()}&submit=Submit"//"https://www.webpagetest.org/runtest.php"//"http://192.168.115.88/?data=123&submit=Submit"

        val response = try {
                URL(url)
                        .openStream()
                        .bufferedReader()
                        .use { it.readText() }
            }
            catch (e: IOException)
            {
                println("Error with ${e.message}.")

        }
        val txtBox = GetMessageTextBox();
        writeToFile(GetMessageTextBox().getText().toString(),LAST_SEND_TEXT)
        writeToFile(GetIpTextBox().getText().toString(),LAST_IP)
        //sendGet()
        Log.i(TAG, response.toString())
        // txtMessage.setText(response)
    }



/*
    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        val txtBox = GetTextBox()

       //outState.putCharSequence("savedText", txtBox.getText())
       // writeToFile(txtBox.getText().toString())
        // Log.i(TAG, "onSaveInstanceState")
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        val readText = ReadFile() //savedInstanceState.getCharSequence("savedText")
        val txtBox = GetTextBox()
        txtBox.setText(readText)
    }
*/
    fun GetMessageTextBox():EditText{
        val textBox: EditText = findViewById(R.id.txtMessage)
        return textBox
    }

    fun GetIpTextBox():EditText{
        val textBox: EditText = findViewById(R.id.txtIp)
        return textBox
    }



    fun ReadFile(fileName:String):String
    {
        try{
            //val path = File(Environment.getExternalStorageDirectory(),"/MyMessanger/")
            val strData = File(path,fileName).readText()

            return strData
        } catch (exception: Exception){
            Log.i(TAG, exception.message.toString())
        }
        return ""
    }


    private fun setupPermissions() {


        val permission = ContextCompat.checkSelfPermission(this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE)

        if (permission != PackageManager.PERMISSION_GRANTED)
        {
            Log.i(TAG, "Permission to record denied")
            ActivityCompat.requestPermissions(this@MainActivity,
                    arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE), 1)

            if (ActivityCompat.shouldShowRequestPermissionRationale(this@MainActivity,
                            Manifest.permission.WRITE_EXTERNAL_STORAGE))
            {
                ActivityCompat.requestPermissions(this@MainActivity,
                        arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE), 1)
            }
        }

        else
        {
            Log.i(TAG, "Permission to record Granted")
        }

    }

    fun writeToFile(txtToWrite:String,fileName:String) {
        //Define ruta en almacenamiento externo y si deseas un directorio.
        setupPermissions()
     //   val path = File(Environment.getExternalStorageDirectory(),"/MyMessanger/")
        var success = true
        //Si el path no existe, trata de crear el directorio.
        if (!path.exists()) {
            success = path.mkdir()
            Log.d(TAG, "Directory " + path + " was created: "  +success)
        }

        //Si el path existe o creo directorio sin problemas ahora crea archivo.
        if (success) {
            Log.d(TAG, "Directory exist, proceed to create the file.")

            //Write text to file!
            val fileToWrite = File(path,fileName).writeText(txtToWrite)
            Toast.makeText(this, "A message was written to your file!", Toast.LENGTH_SHORT).show()

        }
    }

/*

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        when (requestCode) {
            REQUEST_PERMISSION -> if (grantResults.size > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Log.d(TAG, "Permissions were accepted!")
            }
        }
    }
*/
}