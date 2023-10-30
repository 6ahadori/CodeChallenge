package com.bahadori.codechallenge.core.network

import com.bahadori.codechallenge.core.network.model.RatesDto

interface NextopNetworkDataSource {

    suspend fun getRates(): RatesDto
}