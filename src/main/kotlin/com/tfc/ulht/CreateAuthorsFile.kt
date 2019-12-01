package com.tfc.ulht

import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.fileEditor.FileEditorManager
import java.io.BufferedWriter
import java.io.FileWriter
import javax.swing.JOptionPane

class CreateAuthorsFile : AnAction() {

    override fun actionPerformed(e: AnActionEvent) {

        val students = JOptionPane.showInputDialog(
            "Insira o numero do aluno seguido do nome, no seguinte formato:\n" +
                    "NUMERO_ALUNO;NOME_DO_ALUNO"
        )

        val projectDirectory = e.project?.let { FileEditorManager.getInstance(it).project.basePath.toString() }

        val writer = BufferedWriter(FileWriter("$projectDirectory/AUTHORS.txt"))

        val splitStudents = students.split(",")


        for (i in splitStudents.indices) {
            val validStudentNumber = splitStudents[i].trim().split(";")
            for (j in validStudentNumber.indices) {
                if (j.rem(2) == 0) {
                    if (validStudentNumber[0].contains('a') && validStudentNumber[0].length == 9) {
                        writer.write(splitStudents[i].trim())
                        writer.newLine();
                    } else {
                        JOptionPane.showMessageDialog(
                            null,
                            "Numero de aluno invalido!",
                            "Erro",
                            JOptionPane.ERROR_MESSAGE
                        )

                        break
                    }
                }
            }
        }

        writer.close()

        JOptionPane.showMessageDialog(null, "AUTHORS.txt criado com successo")

    }


}
