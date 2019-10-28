package com.tfc.ulht

/**
 *  Open authors LinkedIn webpage
 */

import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.project.DumbAware
import java.lang.Exception
import java.net.URI

class AboutMe: DumbAware, AnAction() {
    /**
     * Function receives an event and calls the openURI which will open the link in the
     * @param myProfile
     */

    override fun actionPerformed(e: AnActionEvent) {
        val myProfile = "https://www.linkedin.com/in/yash-j-1b0b28109/"
        val url = URI(myProfile)
        openURI(url)
    }

    fun openURI(url: URI) {
        try {
            val desktop = java.awt.Desktop.getDesktop()
            desktop.browse(url)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }


}