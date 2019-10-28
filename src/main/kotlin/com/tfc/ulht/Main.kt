package com.tfc.ulht

/**
 * Main class para funcionalidade principal do plugin
 */

import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.fileChooser.FileChooser
import com.intellij.openapi.fileChooser.FileChooserDescriptor
import com.intellij.openapi.ui.Messages
import com.intellij.ui.layout.chooseFile
import java.util.function.Consumer

class Main: AnAction() {
    override fun actionPerformed(e: AnActionEvent) {
        val fileChooserDescriptor = FileChooserDescriptor(
            false,
            true,
            false,
            false,
            false,
            false)

        fileChooserDescriptor.title = "Submit"
        fileChooserDescriptor.description = "Choose your project folder"

        FileChooser.chooseFile(fileChooserDescriptor, e.project, null, com.intellij.util.Consumer {
            Messages.showMessageDialog(e.project, it.path, "Path", Messages.getInformationIcon())
        })

    }
}