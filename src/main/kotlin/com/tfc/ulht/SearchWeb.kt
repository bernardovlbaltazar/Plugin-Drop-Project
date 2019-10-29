package com.tfc.ulht

import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.ui.Messages
import java.awt.Desktop
import java.lang.Exception
import java.net.URI

class SearchWeb: AnAction() {
    override fun actionPerformed(e: AnActionEvent) {
        var searchFor = Messages.showInputDialog(e.project, "What do you want to search?", "Search", Messages.getQuestionIcon())

        val url = URI(searchFor)
        searchWeb(url)
    }

    private fun searchWeb(url: URI) {
        try {
            val googleLink = "https://www.google.com/search?q="
            val openWeb = java.awt.Desktop.getDesktop()
            openWeb.browse(URI(googleLink + url))
        } catch (e: Exception) {
            e.printStackTrace()
        }

        // TODO Returns NullPointerException when pressing the "Cancel" button on the dialog
    }
}
