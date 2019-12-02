package com.tfc.ulht

import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.fileEditor.FileEditorManager
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.io.IOException
import java.util.zip.ZipEntry
import java.util.zip.ZipOutputStream


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

    override fun actionPerformed(e: AnActionEvent) {

        // TODO: Plugin cria zip, agora falta implementar algo para zippar so alguns ficheiros (/src, AUTHORS.txt, etc.)

        val projectDirectory = e.project?.let { FileEditorManager.getInstance(it).project.basePath.toString() }

        println(projectDirectory)

        try {
            val dir = File("$projectDirectory")
            val dirPath = dir.absolutePath
            val obj = ZipFolder()

            obj.listFiles(dir)

            val zipFile = File("$projectDirectory/projeto.zip")


            val fos = FileOutputStream(zipFile)
            val zos = ZipOutputStream(fos)

            val buffer = ByteArray(1024)
            var len: Int

            for (path: String in Companion.totalFiles) {
                val ipfile = File(path)

                val zippath = path.substring(dirPath.length + 1, path.length)
                val zen = ZipEntry(zippath)

                zos.putNextEntry(zen)

                val fis = FileInputStream(ipfile)

                while (fis.read(buffer).also { len = it } > 0) {
                    zos.write(buffer, 0, len)
                }

                zos.closeEntry()
                fis.close()
            }

            zos.close()
            fos.close()

        } catch (e: IOException) {
            e.printStackTrace()
        }

    }

    private fun listFiles(dir: File)  {
        val files = dir.listFiles()

        for (file in files) {
            if (file.isDirectory) {
                listFiles(file)
            } else {
                totalFiles.add(file.absolutePath)
            }
        }
    }

    companion object {
        var totalFiles = ArrayList<String>()
    }
}