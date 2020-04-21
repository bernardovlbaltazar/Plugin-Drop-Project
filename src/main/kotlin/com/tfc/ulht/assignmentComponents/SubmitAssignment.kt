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
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import com.squareup.moshi.Moshi
import com.tfc.ulht.loginComponents.Authentication
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.Request
import okhttp3.RequestBody
import java.io.File
import java.net.URI
import javax.swing.JOptionPane
import javax.swing.JPanel


class SubmitAssignment : AnAction() {

    override fun actionPerformed(e: AnActionEvent) {

        if (!Authentication.alreadyLoggedIn) {
            // If user is has not logged in, show an error message
            JOptionPane.showMessageDialog(null, "You need to login before submiting an assignment", "Submit", JOptionPane.ERROR_MESSAGE)

        } else if (ListAssignment.selectedAssignmentId.isEmpty()) {
            // Before trying to submit project, check if an assignment has been choosen
            JOptionPane.showMessageDialog(null, "You need to choose an assignment first", "Submit", JOptionPane.INFORMATION_MESSAGE)
        } else {
            // If assignment has been choosen, upload zip file
            val projectDirectory = e.project?.let { FileEditorManager.getInstance(it).project.basePath.toString() }
            val body: RequestBody = MultipartBody.Builder().setType(MultipartBody.FORM)
                .addFormDataPart(
                    "file", "projeto.zip",
                    RequestBody.create(
                        MediaType.parse("application/octet-stream"),
                        File("${projectDirectory}\\projeto.zip")
                    )
                )
                .addFormDataPart("assignmentId", ListAssignment.selectedAssignmentId)
                .build()

            val request: Request = Request.Builder()
                .url("http://localhost:8080/upload/")
                .method("POST", body)
                .build()

            val moshi = Moshi.Builder().build()
            val submissionJsonAdapter = moshi.adapter(SubmissionId::class.java)

            var response = Authentication.httpClient.newCall(request).execute()

            val submission = submissionJsonAdapter.fromJson(response.body()!!.source())!!

            /*JPanel myPanel;
            myPanel.add(new JBCefBrowser(“https://www.jetbrains.com”).getComponent());*/

            val desktop = java.awt.Desktop.getDesktop()
            desktop.browse(URI("http://localhost:8080/buildReport/${submission.submissionNumber}"))
        }
    }
}

@JsonClass(generateAdapter = true)
data class SubmissionId(@Json(name = "submissionId") val submissionNumber: Int)