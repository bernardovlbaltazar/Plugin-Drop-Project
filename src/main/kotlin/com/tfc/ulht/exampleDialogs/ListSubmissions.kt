package com.tfc.ulht.exampleDialogs

import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import okhttp3.*
import java.io.*


class ListSubmissions : AnAction() {

    override fun actionPerformed(e: AnActionEvent) {

        val httpClient = OkHttpClient.Builder()
            .authenticator(object : Authenticator {
                @Throws(IOException::class)
                override fun authenticate(route: Route?, response: Response): Request? {
                    if (response.request().header("Authorization") != null) {
                        return null // Give up, we've already attempted to authenticate.
                    }

                    return response.request().newBuilder()
                        .header("Authorization", Credentials.basic("student1", "123"))
                        .build()
                }
            })
            .build()

        val request = Request.Builder()
            .url("http://drop-project-tfc.herokuapp.com/")
            .build()

        httpClient.newCall(request).execute().use { response ->
            if (!response.isSuccessful) throw IOException("Unexpected code $response")

            println(response.body()!!.string())
        }




    }

}