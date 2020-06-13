package com.android.encryptions.algorithms

import android.util.Base64
import android.util.Log
import com.android.encryptions.constants.Constants
import java.security.NoSuchAlgorithmException
import javax.crypto.Cipher
import javax.crypto.KeyGenerator
import javax.crypto.SecretKey
import javax.crypto.spec.IvParameterSpec

class AESEncryption {

    private val encryptionTransformation = "AES/CBC/PKCS5Padding"
    private val initializationVector = "android123456789"
    private val iv: ByteArray = initializationVector.toByteArray(charset("UTF-8"))
    private val keySize = 256

    fun encrypt(plainText:String, secretKey: SecretKey):String{
        val byteArray =  plainText.toByteArray(charset("UTF-8"))
        val cipher = Cipher.getInstance(encryptionTransformation)
        cipher.init(Cipher.ENCRYPT_MODE, secretKey, IvParameterSpec(iv))
        val encryptedData = cipher.doFinal(byteArray)

       return Base64.encodeToString(encryptedData, Base64.NO_WRAP)
    }

    fun decrypt(base64String: String, secretKey: SecretKey): String{

        val byteArray = Base64.decode(base64String, Base64.NO_WRAP)
        val cipher = Cipher.getInstance(encryptionTransformation)
        cipher.init(Cipher.DECRYPT_MODE, secretKey, IvParameterSpec(iv))

        val decryptedData = cipher.doFinal(byteArray)
        val decryptedString = String(decryptedData)

        return decryptedString
    }

    fun generateSymmetricKey(): SecretKey? {
        var secretKey: SecretKey? = null
        try {
            val keyGen = KeyGenerator.getInstance(Constants.AES_ENCRYPTION)
            keyGen.init(keySize)
            secretKey = keyGen.generateKey()

        } catch (e: NoSuchAlgorithmException) {
        }
        return secretKey
    }

}