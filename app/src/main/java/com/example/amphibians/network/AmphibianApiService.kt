package com.example.amphibians.network

import retrofit2.http.GET
import com.example.amphibians.model.AmphibianInfo

interface AmphibianApiService {
    @GET("amphibians")
    suspend fun getAmphibians(): List<AmphibianInfo>
}