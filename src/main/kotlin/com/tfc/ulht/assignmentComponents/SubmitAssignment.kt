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

package com.tfc.ulht.assignmentComponents

import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.fileEditor.FileEditorManager
import com.jetbrains.rd.util.string.printToString
import com.tfc.ulht.loginComponents.Authentication
import okhttp3.*
import java.io.File


class SubmitAssignment: AnAction() {

    override fun actionPerformed(e: AnActionEvent) {
        // Before trying to submit project, check if an assignment has been choosen
        if (!ListAssignment.selectedAssignmentId.isEmpty() && !Authentication.alreadyLoggedIn) {

        } else {
            // If assignment has been choosen, upload zip file
            val projectDirectory = e.project?.let { FileEditorManager.getInstance(it).project.basePath.toString() }
            val body: RequestBody = MultipartBody.Builder().setType(MultipartBody.FORM)
                .addFormDataPart(
                    "file", "/${projectDirectory}\\projeto.zip",
                    RequestBody.create(
                        MediaType.parse("application/octet-stream"),
                        File("/${projectDirectory}\\projeto.zip")
                    )
                )
                .addFormDataPart("assignmentId", ListAssignment.selectedAssignmentId)
                .build()

            val request: Request = Request.Builder()
                .url("http://localhost:8080/upload/")
                .method("POST", body)
                .build()

            val response: Response = Authentication.httpClient.newCall(request).execute()
            response.close()

        }
    }

    /*var client = OkHttpClient().newBuilder()
        .build()
    var mediaType: MediaType = MediaType.parse("text/plain")
    var body: RequestBody = MultipartBody.Builder().setType(MultipartBody.FORM)
        .addFormDataPart(
            "file", "/C:/Users/yashj/IdeaProjects/Base de dados/projeto.zip",
            RequestBody.create(
                MediaType.parse("application/octet-stream"),
                File("/C:/Users/yashj/IdeaProjects/Base de dados/projeto.zip")
            )
        )
        .addFormDataPart("assignmentId", "sampleJavaProject")
        .build()
    var request: Request = Builder()
        .url("http://localhost:8080/upload/")
        .method("POST", body)
        .addHeader("Cookie", "JSESSIONID=84F42716FBDDD77C0CC34EFEE4C285BB")
        .build()
    var response: Response = client.newCall(request).execute()*/
}