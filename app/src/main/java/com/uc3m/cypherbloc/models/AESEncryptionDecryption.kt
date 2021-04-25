package com.uc3m.cypherbloc.models

//import javax.crypto.KeyGenerator
import java.security.NoSuchAlgorithmException
import java.security.spec.InvalidKeySpecException
import javax.crypto.Cipher
import javax.crypto.SecretKey
import javax.crypto.SecretKeyFactory
import javax.crypto.spec.IvParameterSpec
import javax.crypto.spec.PBEKeySpec
import javax.crypto.spec.SecretKeySpec


class AESEncryptionDecryption {

    val salt = byteArrayOf(50, 111, 8, 53, 86, 35, -19, -47)
    var data: MutableList<ByteArray> = mutableListOf()


    fun isExpectedPassword(pwdHash: ByteArray, expectedHash: ByteArray): Boolean {
        if (pwdHash.size != expectedHash.size) return false
        return pwdHash.indices.all { pwdHash[it] == expectedHash[it] }
    }

    fun hash(password: String): ByteArray {
        val spec = PBEKeySpec(password.toCharArray(), salt, 65536, 256)
        try {
            val skf = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1")
            return skf.generateSecret(spec).encoded
        } catch (e: NoSuchAlgorithmException) {
            throw AssertionError("Error while hashing a password: " + e.message, e)
        } catch (e: InvalidKeySpecException) {
            throw AssertionError("Error while hashing a password: " + e.message, e)
        } finally {
            spec.clearPassword()
        }
    }

    fun encrypt(strToEncrypt: String, password: String): ByteArray {

        //Creating manual password  -> secret key
        val passHash = hash(password)
        val key: SecretKey = SecretKeySpec(passHash, "AES")

        var cipher = Cipher.getInstance("AES/CBC/PKCS5Padding")
        cipher.init(Cipher.ENCRYPT_MODE, key)

        val ciphertext = cipher.doFinal(strToEncrypt.toByteArray(charset("UTF-8")))

        data.add(passHash)
        data.add(cipher.iv)

        //saveSecretKey(context, passHash)
        //saveInitializationVector(context, cipher.iv)

        return ciphertext
    }

    fun decrypt(password: String, dataToDecrypt: Notes): String? {
        //transforming input password to hashPassword to compare
        val keySaved2 = dataToDecrypt.pass
        val pwdHash = hash(password)
        if (!isExpectedPassword(pwdHash, keySaved2)) return null

        //Creating key from hash password
        val key: SecretKey = SecretKeySpec(pwdHash, "AES")

        val cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING")
        val ivSpec = IvParameterSpec(dataToDecrypt.iv)

        //Process init
        cipher.init(Cipher.DECRYPT_MODE, key, ivSpec)
        val cipherText = cipher.doFinal(dataToDecrypt.content)

        val sb = StringBuilder()
        for (b in cipherText) {
            sb.append(b.toChar())
        }

        return sb.toString()
    }

}