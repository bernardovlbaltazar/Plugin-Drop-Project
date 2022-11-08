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

package com.tfc.ulht.loginComponents

import com.jetbrains.rd.util.use
import com.tfc.ulht.Globals
import okhttp3.*
import java.io.IOException
import java.security.SecureRandom
import java.security.cert.X509Certificate
import javax.net.ssl.HostnameVerifier
import javax.net.ssl.SSLContext
import javax.net.ssl.TrustManager
import javax.net.ssl.X509TrustManager

class Authentication {

    companion object {
        var httpClient = OkHttpClient()
        var alreadyLoggedIn = false
    }

    var serverResponse: Boolean = false
    private val REQUEST_URL = "${Globals.REQUEST_URL}/api/student/assignments/current"

    fun authenticate(username: String, token: String): Boolean {
        httpClient = OkHttpClient.Builder()
            .authenticator(object : Authenticator {
                @Throws(IOException::class)
                override fun authenticate(route: Route?, response: Response): Request? {
                    if (response.request().header("Authorization") != null) {
                        return null // Give up, we've already attempted to authenticate.
                    }

                    return response.request().newBuilder()
                        .header("Authorization", Credentials.basic(username, token))
                        .build()
                }
            })
            .ignoreAllSSLErrors()
            .build()
        val request = Request.Builder()
            .url(/*Globals.*/REQUEST_URL)
            .build()

        httpClient.newCall(request).execute().use { response ->
            alreadyLoggedIn = response.isSuccessful
        }

        return alreadyLoggedIn
    }
    fun checkCredentials(username: String, password: String, firstRun: Boolean = false): Boolean {

        httpClient = OkHttpClient.Builder()
            .authenticator(object : Authenticator {
                @Throws(IOException::class)
                override fun authenticate(route: Route?, response: Response): Request? {
                    if (response.request().header("Authorization") != null) {
                        return null // Give up, we've already attempted to authenticate.
                    }

                    return response.request().newBuilder()
                        .header("Authorization", Credentials.basic(username, password))
                        .build()
                }
            })
            .build()
        val request = Request.Builder()
            .url(Globals.REQUEST_URL)
            .build()

        httpClient.newCall(request).execute().use { response ->
            serverResponse = response.isSuccessful
        }

        println(serverResponse)

        if (serverResponse && !firstRun) {
            CredentialsController().encryptPassword(username, password)
            alreadyLoggedIn = true
        }

        return serverResponse
    }


    fun OkHttpClient.Builder.ignoreAllSSLErrors(): OkHttpClient.Builder {
        val naiveTrustManager = object : X509TrustManager {
            override fun getAcceptedIssuers(): Array<X509Certificate> = arrayOf()
            override fun checkClientTrusted(certs: Array<X509Certificate>, authType: String) = Unit
            override fun checkServerTrusted(certs: Array<X509Certificate>, authType: String) = Unit
        }

        val insecureSocketFactory = SSLContext.getInstance("TLSv1.2").apply {
            val trustAllCerts = arrayOf<TrustManager>(naiveTrustManager)
            init(null, trustAllCerts, SecureRandom())
        }.socketFactory

        sslSocketFactory(insecureSocketFactory, naiveTrustManager)
        hostnameVerifier(HostnameVerifier { _, _ -> true })
        return this
    }
}