package com.tfc.ulht

import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.fileEditor.FileEditorManager
import com.tfc.ulht.loginComponents.LoginDialog.Companion.studentsList
import java.io.BufferedWriter
import java.io.FileWriter
import javax.swing.JOptionPane

class CreateAuthorsFile : AnAction() {


    override fun actionPerformed(e: AnActionEvent) {

        val projectDirectory = e.project?.let { FileEditorManager.getInstance(it).project.basePath.toString() }
        val writer = BufferedWriter(FileWriter("$projectDirectory/AUTHORS.txt"))


        for (students in studentsList) {
            println(students.toString())
            writer.write(students.toString())
            writer.newLine()
        }

        writer.close()

        JOptionPane.showMessageDialog(null, "AUTHORS.txt criado com successo")

    }


}
