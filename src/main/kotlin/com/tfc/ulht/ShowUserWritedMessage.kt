package com.tfc.ulht

import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.ui.InputValidator
import com.intellij.openapi.ui.Messages

class ShowUserWritedMessage: AnAction() {
    override fun actionPerformed(e: AnActionEvent) {
        val userMessage = Messages.showInputDialogWithCheckBox("Hello", "Title"
        , "Do you allow me to read the message?", false,
            true, Messages.getQuestionIcon(), "0", null)

        if (userMessage.second) {
            Messages.showMessageDialog(e.project, userMessage.first, "Your Message", Messages.getInformationIcon())
        } else {
            Messages.showErrorDialog(e.project, "You did not allow me permission", "Error!")
        }
    }
}