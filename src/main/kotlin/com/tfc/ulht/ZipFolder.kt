package com.tfc.ulht

import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.fileEditor.FileEditorManager
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.*
import java.util.zip.ZipOutputStream;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.zip.ZipEntry;



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

        // TODO: Melhor apagR

        /**
         * Returns project path on system. Ex: C:/Users/yashj/IdeaProjects/Base de dados
         */
        val projectDirectory = e.project?.let { FileEditorManager.getInstance(it).project.basePath.toString() }
        println(projectDirectory)

        var zipDir = "${projectDirectory}\\projeto.zip"

        zipFolderStructure(projectDirectory, zipDir)


    }

    private fun zipFolderStructure(projectDirectory: String?, zipDir: String){
        try {
            FileOutputStream(zipDir).use { fos-> ZipOutputStream(fos).use { zos->
                val sourcePath = Paths.get(projectDirectory)
                Files.walkFileTree(sourcePath, object:SimpleFileVisitor<Path>() {
                    @Throws(IOException::class)
                    override
                    fun preVisitDirectory(dir:Path, attrs:BasicFileAttributes):FileVisitResult {
                        if (sourcePath != dir && dir.toString().startsWith("$sourcePath\\src")) {
                            zos.putNextEntry(ZipEntry(sourcePath.relativize(dir).toString() + "/"))
                            zos.closeEntry()
                        }
                        return FileVisitResult.CONTINUE
                    }
                    @Throws(IOException::class)
                    override
                    fun visitFile(file:Path, attrs:BasicFileAttributes):FileVisitResult {
                        if (sourcePath.relativize(file).toString() == "AUTHORS.txt"
                            || file.toString().startsWith("$sourcePath\\src")) {
                            zos.putNextEntry(ZipEntry(sourcePath.relativize(file).toString()))
                            Files.copy(file, zos)
                            zos.closeEntry()
                            return FileVisitResult.CONTINUE
                        }

                        return FileVisitResult.CONTINUE
                    }
                })
            } }

        } catch (e: IOException) {
            e.printStackTrace()
        }
    }
}
