package com.tfc.ulht

import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.fileEditor.FileEditorManager
import net.lingala.zip4j.ZipFile
import java.io.File
import java.util.*


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

class ZipFolder : AnAction() {

    companion object {
        var totalFiles = ArrayList<String>()
    }

    override fun actionPerformed(e: AnActionEvent) {

        /**
         * Returns project path on system. Ex: C:/Users/yashj/IdeaProjects/Base de dados
         */
        val projectDirectory = e.project?.let { FileEditorManager.getInstance(it).project.basePath.toString() }


        /***
         * https://github.com/srikanth-lingala/zip4j
         *
         * Library reduces boilerplate when creating Zip file and Drop Project
         * is only accepting a zip file created using this library
         */
        // Add AUTHORS.txt to a new zip
        ZipFile("$projectDirectory\\projeto.zip")
            .addFile(File("$projectDirectory\\AUTHORS.txt"))


        // Add the "src" folder on the existing zip
        ZipFile("$projectDirectory\\projeto.zip")
            .addFolder(File("$projectDirectory\\src"))
    }


}
