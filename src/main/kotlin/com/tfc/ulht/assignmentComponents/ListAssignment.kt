package com.tfc.ulht.assignmentComponents

import assignmentTable.AssignmentTableColumn
import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import com.tfc.ulht.loginComponents.Authentication
import data.Assignment
import okhttp3.Request
import java.lang.reflect.Type
import javax.swing.JOptionPane


class ListAssignment : AnAction() {

    companion object {
        var selectedAssignmentId: String = ""
    }

    val type: Type = Types.newParameterizedType(
        List::class.java,
        Assignment::class.java
    )

    private val REQUEST_URL = "https://drop-project-fork.herokuapp.com/api/v1/assignmentList"
    private var assignmentList = listOf<Assignment>()
    private val moshi = Moshi.Builder().build()
    private val assignmentJsonAdapter: JsonAdapter<List<Assignment>> = moshi.adapter(type)


    override fun actionPerformed(e: AnActionEvent) {

        if (Authentication.alreadyLoggedIn) {
            val request = Request.Builder()
                .url(REQUEST_URL)
                .build()

            Authentication.httpClient.newCall(request).execute().use { response ->
                assignmentList = assignmentJsonAdapter.fromJson(response.body()!!.source())!!

            }
            showAssingmentTable()

        } else {
            JOptionPane.showMessageDialog(null, "You need to login first!", "Login First", JOptionPane.WARNING_MESSAGE)
        }
    }

    private fun showAssingmentTable() {
        // TODO: Create submissions dialog
        AssignmentTableColumn(assignmentList)
    }



}