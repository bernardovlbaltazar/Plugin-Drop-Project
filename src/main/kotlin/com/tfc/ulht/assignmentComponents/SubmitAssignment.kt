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
import com.intellij.openapi.ui.Messages
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import com.squareup.moshi.Moshi
import com.tfc.ulht.Globals
import com.tfc.ulht.SubmissionListener
import com.tfc.ulht.ZipFolder
import com.tfc.ulht.loginComponents.Authentication
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.Request
import okhttp3.RequestBody
import java.io.File
import java.util.*
import javax.swing.JOptionPane
import kotlin.collections.ArrayList


class SubmitAssignment : AnAction() {

    private val REQUEST_URL = "${Globals.REQUEST_URL}/upload"
    private val listeners: MutableList<SubmissionListener> = ArrayList()

    fun addListener(toAdd: SubmissionListener) {
        listeners.add(toAdd)
    }


    override fun actionPerformed(e: AnActionEvent) {

        if (!Authentication.alreadyLoggedIn) {
            // If user is has not logged in, show an error message
            JOptionPane.showMessageDialog(null, "You need to login before submiting an assignment", "Submit", JOptionPane.ERROR_MESSAGE)

        } else if (Globals.selectedAssignmentID.isEmpty()) {
            // Before trying to submit project, check if an assignment has been choosen
            JOptionPane.showMessageDialog(null, "You need to choose an assignment first", "Submit", JOptionPane.INFORMATION_MESSAGE)
        } else {
            // If assignment has been choosen, upload zip file
            ZipFolder().actionPerformed(e)
            val projectDirectory = e.project?.let { FileEditorManager.getInstance(it).project.basePath.toString() }
            val body: RequestBody = MultipartBody.Builder().setType(MultipartBody.FORM)
                .addFormDataPart(
                    "file", "projeto.zip",
                    RequestBody.create(
                        MediaType.parse("application/octet-stream"),
                        File("${projectDirectory}\\projeto.zip")
                    )
                )
                .addFormDataPart("assignmentId", Globals.selectedAssignmentID)
                .build()

            val request: Request = Request.Builder()
                .url(REQUEST_URL)
                .method("POST", body)
                .build()

            val moshi = Moshi.Builder().build()
            val submissionJsonAdapter = moshi.adapter(SubmissionId::class.java)

            Authentication.httpClient.newCall(request).execute().use { response ->
                if (response.isSuccessful) {
                    if (response.code() == 200) {
                        val submission = submissionJsonAdapter.fromJson(response.body()!!.source())!!
                    }
                } else if (!response.isSuccessful && response.code() == 500) {
                    val errorJsonAdapter = moshi.adapter(ErrorMessage::class.java)
                    val errorMessage = errorJsonAdapter.fromJson(response.body()!!.source())!!
                    Messages.showMessageDialog(errorMessage.error, "Submission", Messages.getErrorIcon())
                }
            }

        }
    }
}

@JsonClass(generateAdapter = true)
data class SubmissionId(@Json(name = "submissionId") val submissionNumber: Int)

@JsonClass(generateAdapter = true)
data class ErrorMessage(val error: String)