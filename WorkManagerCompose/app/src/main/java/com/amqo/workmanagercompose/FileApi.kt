package com.amqo.workmanagercompose

import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.http.GET

interface FileApi {

    @GET("/STScI-01GA76RM0C11W977JRHGJ5J26X.png")
    suspend fun downloadImage(): Response<ResponseBody>

    companion object {
        val instance: FileApi by lazy {
            Retrofit.Builder()
                .baseUrl("https://stsci-opo.org")
                .build()
                .create(FileApi::class.java)
        }
    }
}