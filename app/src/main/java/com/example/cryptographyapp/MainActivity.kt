package com.example.cryptographyapp

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.telephony.SmsManager
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {

    private val Key="KEY";

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        var plainText=findViewById<EditText>(R.id.et1);// input text
        var textViewEncrypted=findViewById<TextView>(R.id.tv3); //encrypted text
        var textViewDecrypted=findViewById<TextView>(R.id.tv4); //decrypted text
        val cipherButton=findViewById<Button>(R.id.b1) //encrypt button
        val sendMsgButton=findViewById<Button>(R.id.b2) //sms button



        cipherButton.setOnClickListener{   // working of encrypt button
            val input=plainText.text.toString()
            val encryptedText=encrypt(input,Key); //encrypt text
            val decryptedText=decrypt(encryptedText,Key);  //decrypt encrypted text

            textViewEncrypted.text= "Encrypted Text: $encryptedText" //display encrypted tetx
            textViewDecrypted.text="Decrypted Text: $decryptedText" //display decrypted text
        }

        sendMsgButton.setOnClickListener{
            val phone="8197742770";
            val encryptedText = textViewEncrypted.text.toString().removePrefix("Encrypted Text: ").trim()
            if(encryptedText.isNotEmpty()){
                sendSms(phone,encryptedText) //sending sms
            }else{
                Toast.makeText(this,"Please Encrypt First",Toast.LENGTH_SHORT).show()
            }
        }

    }

    private fun encrypt(text: String, key: String): String {
        val result = StringBuilder()
        for (i in text.indices) {
            val c = text[i]
            if (c.isLetter()) {
                val offset = if (c.isUpperCase()) 'A' else 'a'
                val keyChar = key[i % key.length].uppercaseChar() - 'A'
                result.append(((c - offset + keyChar) % 26 + offset.code).toChar())
            } else {
                result.append(c)
            }
        }
        return result.toString()
    }
    private fun decrypt(text: String, key: String): String {
        val result = StringBuilder()
        for (i in text.indices) {
            val c = text[i]
            if (c.isLetter()) {
                val offset = if (c.isUpperCase()) 'A' else 'a'
                val keyChar = key[i % key.length].uppercaseChar() - 'A'
                result.append(((c - offset - keyChar + 26) % 26 + offset.code).toChar())
            } else {
                result.append(c)
            }
        }
        return result.toString()
    }

    private fun sendSms(phoneNumber: String, message: String) {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.SEND_SMS)
            != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.SEND_SMS), 1)
        } else {
            val smsManager = SmsManager.getDefault()
            smsManager.sendTextMessage(phoneNumber, null, message, null, null)
            Toast.makeText(this, "SMS sent!", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == 1 && grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(this, "Permission granted. Tap again to send SMS.", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(this, "Permission denied!", Toast.LENGTH_SHORT).show()
        }
    }
}