package com.tfc.ulht.loginComponents

import com.intellij.openapi.application.PreloadingActivity
import com.intellij.openapi.progress.ProgressIndicator
import java.io.File
import javax.swing.JOptionPane

class CredentialsController: PreloadingActivity() {

    companion object {
        lateinit var e: String
    }

    override fun preload(indicator: ProgressIndicator) {
        JOptionPane.showMessageDialog(null,
            "Yes",
            "Dunno",
            JOptionPane.PLAIN_MESSAGE)
    }

    fun encryptPassword(username: String, password: String) {
        val enc = String(encrypt(password.toByteArray()))

        val file = File("$e\\up.txt")
        file.writeText("$username;$enc")

        println("Original: $password")
        println("Encrypted: $enc")

    }


    private fun encrypt(textToEncrypt: ByteArray): ByteArray {
        val enc = ByteArray(textToEncrypt.size)

        for (i in textToEncrypt.indices) {
            enc[i] = (if ((i % 2 == 0)) textToEncrypt[i] + 1  else textToEncrypt[i] - 1).toByte()
        }

        return enc
    }

    fun decrypt(textToDecrypt: ByteArray): ByteArray {
        val enc = ByteArray(textToDecrypt.size)

        for (i in textToDecrypt.indices) {
            enc[i] = (if ((i % 2 == 0)) textToDecrypt[i] - 1  else textToDecrypt[i] + 1).toByte()
        }

        return enc
    }


}