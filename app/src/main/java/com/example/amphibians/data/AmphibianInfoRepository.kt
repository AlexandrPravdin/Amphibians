package com.example.amphibians.data

import com.example.amphibians.network.AmphibianApiService
import com.example.amphibians.model.AmphibianInfo

interface AmphibianInfoRepository {
    suspend fun getAmphibians(): List<AmphibianInfo>
}

class NetworkAmphibianRepository(
    private val amphibianApiService: AmphibianApiService
) : AmphibianInfoRepository {

    override suspend fun getAmphibians(): List<AmphibianInfo> = amphibianApiService.getAmphibians()
}