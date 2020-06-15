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

package com.tfc.ulht.submissionComponents

import assignmentTable.SubmissionTableColumn
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import com.tfc.ulht.loginComponents.Authentication
import java.lang.reflect.Type
import data.Submission
import okhttp3.Request

class ListSubmissions(val assignmentId: String) {

    companion object {
        var selectedSubmission: String = ""
    }

    var type: Type = Types.newParameterizedType(
        List::class.java,
        Submission::class.java
    )

    private val REQUEST_URL = "https://drop-project-fork.herokuapp.com/api/v1/submissionsList"
    private var submissionList = listOf<Submission>()
    private val moshi = Moshi.Builder().build()
    private val submissionJsonAdapter: JsonAdapter<List<Submission>> = moshi.adapter(type)

    init {
        val request = Request.Builder()
            .url("$REQUEST_URL/$assignmentId")
            .build()

        Authentication.httpClient.newCall(request).execute().use { response ->
            submissionList = submissionJsonAdapter.fromJson(response.body()!!.source())!!
        }

        submissionList
        showSubmissionList()
    }

    private fun showSubmissionList() {
        SubmissionTableColumn(submissionList)
    }


}