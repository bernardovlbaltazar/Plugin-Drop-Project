package com.tfc.ulht.submissionComponents

import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import com.tfc.ulht.loginComponents.Authentication
import okhttp3.Request
import java.lang.reflect.Type
import javax.swing.JOptionPane
import kotlin.test.todo


class ListSubmissions : AnAction() {
    val type: Type = Types.newParameterizedType(
        List::class.java,
        Submission::class.java
    )

    private val REQUEST_URL = "http://localhost:8080/api/v1/assignmentList"
    private var assignmentList = listOf<Submission>()
    private val moshi = Moshi.Builder().build()
    private val submissionJsonAdapter: JsonAdapter<List<Submission>> = moshi.adapter(type)

    override fun actionPerformed(e: AnActionEvent) {

        if (Authentication.alreadyLoggedIn) {
            val request = Request.Builder()
                .url(REQUEST_URL)
                .build()

            Authentication.httpClient.newCall(request).execute().use { response ->
                assignmentList = submissionJsonAdapter.fromJson(response.body()!!.source())!!
            }

            showSubmissionDialog()

        } else {
            JOptionPane.showMessageDialog(null, "You need to login first!", "Login First", JOptionPane.WARNING_MESSAGE)
        }
    }

    private fun showSubmissionDialog() {
        // TODO: Create submissions dialog
    }



}