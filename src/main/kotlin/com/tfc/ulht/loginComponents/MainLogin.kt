/*-
 * Plugin Drop Project
 * 
 * Copyright (C) 2019 Yash Jahit
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.tfc.ulht.loginComponents

import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.fileEditor.FileEditorManager
import com.tfc.ulht.CreateAuthorsFile
import com.tfc.ulht.Users
import com.tfc.ulht.ZipFolder
import com.tfc.ulht.loginComponents.Authentication.Companion.alreadyLoggedIn
import java.awt.Dimension
import java.awt.FlowLayout
import java.awt.GridLayout
import java.io.File
import javax.swing.JFrame
import javax.swing.JOptionPane
import javax.swing.JPanel

class MainLogin : AnAction() {

    override fun actionPerformed(e: AnActionEvent) {

        val projectDirectory = e.project?.let { FileEditorManager.getInstance(it).project.basePath.toString() }
        val panel = JPanel()

        if (projectDirectory != null) {
            CredentialsController.e = projectDirectory
        }

        if (!alreadyLoggedIn) {
            LoginDialog().assembleDialog(panel)
        } else {
            JOptionPane.showMessageDialog(null, "You are already logged in",
                "Log in",
                JOptionPane.INFORMATION_MESSAGE)
        }

        val checkFile = File("$projectDirectory/AUTHORS.txt")

        if (!checkFile.exists()) {
            CreateAuthorsFile().actionPerformed(e)
        }

        val checkIfZipExists = File("$projectDirectory/projeto.zip")
        if (!checkIfZipExists.exists()) {
            ZipFolder().actionPerformed(e)
        } else {
            checkIfZipExists.delete()
            ZipFolder().actionPerformed(e)
        }
    }
}