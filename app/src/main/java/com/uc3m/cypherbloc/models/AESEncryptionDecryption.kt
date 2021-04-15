package com.uc3m.cypherbloc.models

import android.content.Context
import android.util.Log
import androidx.preference.PreferenceManager
import android.widget.Toast
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream
import java.io.ObjectInputStream
import java.io.ObjectOutputStream
import java.security.spec.KeySpec
import javax.crypto.Cipher
//import javax.crypto.KeyGenerator
import javax.crypto.SecretKey
import javax.crypto.SecretKeyFactory
import javax.crypto.spec.IvParameterSpec
import javax.crypto.spec.PBEKeySpec
import javax.crypto.spec.SecretKeySpec
import kotlin.system.exitProcess


class AESEncryptionDecryption {


    fun encrypt(context: Context?, strToEncrypt: String, password: CharArray): ByteArray {

        //Creating manual password  -> secret key

        val salt = byteArrayOf(50, 111, 8, 53, 86, 35, -19, -47)

        val factory: SecretKeyFactory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1")
        val spec: KeySpec = PBEKeySpec(password, salt, 65536, 256)
        val tmp: SecretKey = factory.generateSecret(spec)
        val key: SecretKey = SecretKeySpec(tmp.encoded, "AES")

        var cipher = Cipher.getInstance("AES/CBC/PKCS5Padding")
        cipher.init(Cipher.ENCRYPT_MODE, key)

        val iv = cipher.parameters.getParameterSpec(IvParameterSpec::class.java).iv
        val ciphertext = cipher.doFinal(strToEncrypt.toByteArray(charset("UTF-8")))

        Log.d("text to code", strToEncrypt)
        Log.d("coded text", ciphertext.toString())
        Log.d("ivspec", IvParameterSpec(iv).toString())


        // reinit cypher using param spec

        cipher = Cipher.getInstance("AES/CBC/PKCS5Padding")
        cipher.init(Cipher.DECRYPT_MODE, key, IvParameterSpec(iv))
        val aux = cipher.doFinal(ciphertext)
        Log.d("decoded bytearray", aux.toString())
        Log.d("string(byteArray)", String(aux))

        saveSecretKey(context, key)
        saveInitializationVector(context, cipher.iv)


        Toast.makeText(context, "dbg encrypted = [" + ciphertext.toString() + "]", Toast.LENGTH_LONG).show()

        return ciphertext

      /*  val plainText = strToEncrypt.toByteArray(Charsets.UTF_8)
        val keygen = KeyGenerator.getInstance("AES")
        keygen.init(256)
        val key = keygen.generateKey()*/

      /*val cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING")
        cipher.init(Cipher.ENCRYPT_MODE, key)
        val cipherText = cipher.doFinal(plainText)*/

/*
        val sb = StringBuilder()
        for (b in cipherText) {
            sb.append(b.toChar())
        }
 */

    }

    fun decrypt(context: Context, password: CharArray, dataToDecrypt: ByteArray): String {
        //transforming input password to SecretKey to compare
        val salt = byteArrayOf(50, 111, 8, 53, 86, 35, -19, -47)
        val factory: SecretKeyFactory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1")
        val spec: KeySpec = PBEKeySpec(password, salt, 65536, 256)
        val tmp: SecretKey = factory.generateSecret(spec)
        val key: SecretKey = SecretKeySpec(tmp.encoded, "AES")

        //recovering saved SecretKey
        val cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING")
        val ivSpec = IvParameterSpec(getSavedInitializationVector(context))

        Log.d("ivspec decryption", ivSpec.toString())
        if (key != getSavedSecretKey(context)){

        }
        //Process init
        Log.d("dataToDecrypt", dataToDecrypt.toString())
        cipher.init(Cipher.DECRYPT_MODE, key, ivSpec)
        val cipherText = cipher.doFinal(dataToDecrypt)
        Log.d("dataDecrypted", String(cipherText))

        val sb = StringBuilder()
        for (b in cipherText) {
            sb.append(b.toChar())
        }
        Toast.makeText(context, "dbg decrypted = [" + sb.toString() + "]", Toast.LENGTH_LONG).show()

        return sb.toString()
    }

    fun saveSecretKey(context: Context?, key: SecretKey) {
        val baos = ByteArrayOutputStream()
        val oos = ObjectOutputStream(baos)
        oos.writeObject(key)
        val strToSave =
            String(android.util.Base64.encode(baos.toByteArray(), android.util.Base64.DEFAULT))
        val sharedPref = PreferenceManager.getDefaultSharedPreferences(context)
        val editor = sharedPref.edit()
        editor.putString("secret_key", strToSave)
        editor.apply()
    }

    fun getSavedSecretKey(context: Context): SecretKey {
        val sharedPref = PreferenceManager.getDefaultSharedPreferences(context)
        val strSecretKey = sharedPref.getString("secret_key", "")
        val bytes = android.util.Base64.decode(strSecretKey, android.util.Base64.DEFAULT)
        val ois = ObjectInputStream(ByteArrayInputStream(bytes))
        val secretKey = ois.readObject() as SecretKey
        return secretKey
    }

    fun saveInitializationVector(context: Context?, initializationVector: ByteArray) {
        val baos = ByteArrayOutputStream()
        val oos = ObjectOutputStream(baos)
        oos.writeObject(initializationVector)
        val strToSave =
            String(android.util.Base64.encode(baos.toByteArray(), android.util.Base64.DEFAULT))
        val sharedPref = PreferenceManager.getDefaultSharedPreferences(context)
        val editor = sharedPref.edit()
        editor.putString("initialization_vector", strToSave)
        editor.apply()
    }

    fun getSavedInitializationVector(context: Context): ByteArray {
        val sharedPref = PreferenceManager.getDefaultSharedPreferences(context)
        val strInitializationVector = sharedPref.getString("initialization_vector", "")
        val bytes = android.util.Base64.decode(strInitializationVector, android.util.Base64.DEFAULT)
        val ois = ObjectInputStream(ByteArrayInputStream(bytes))
        val initializationVector = ois.readObject() as ByteArray
        return initializationVector
    }
}