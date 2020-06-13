package com.android.encryptions

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import com.android.encryptions.algorithms.AESEncryption
import java.lang.Exception

class MainActivity : AppCompatActivity() {

    private var resultTextView: TextView? = null
    private var decryptEncryptedButton: Button? = null
    val TAG = "Testing"
    var aesEncryption:AESEncryption = AESEncryption();
    var secretKey = aesEncryption.generateSymmetricKey()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val inputvalue = findViewById<EditText>(R.id.inputvalue)
         resultTextView = findViewById<TextView>(R.id.resultValue)
         decryptEncryptedButton = findViewById<Button>(R.id.decryptEncryptedValue)

        findViewById<Button>(R.id.encrypt).setOnClickListener({
            if (secretKey != null) {

                val data = inputvalue.editableText.toString()
                val encryptedData = aesEncryption.encrypt(data, secretKey!!)
                resultTextView?.setText(encryptedData)
                decryptEncryptedButton?.visibility = View.VISIBLE
                Log.d(TAG, encryptedData)
            }
        })

        findViewById<Button>(R.id.decrypt).setOnClickListener({
            if (secretKey != null) {
                val encryptedData = inputvalue.editableText.toString()
                decrypt(encryptedData)

            }
        })

        decryptEncryptedButton?.setOnClickListener({
            val encryptedData = resultTextView?.text.toString()
            Log.d(TAG, encryptedData)
            try {
                decrypt(encryptedData)
            }catch (e:Exception){}

        })

    }

    private fun decrypt(data:String){
    try {
         val decryptedData = aesEncryption.decrypt(data, secretKey!!)
        resultTextView?.setText(decryptedData)
        decryptEncryptedButton?.visibility = View.INVISIBLE

        }catch (e:Exception){}



    }
}
