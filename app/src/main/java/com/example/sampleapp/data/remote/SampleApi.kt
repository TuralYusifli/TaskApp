package com.example.sampleapp.data.remote

import retrofit2.Response
import retrofit2.http.GET

interface SampleApi {
    @GET("tayqa/tiger/api/development/test/TayqaTech/getdata")
    suspend fun getAllData(): Response<ResponseModel>
}