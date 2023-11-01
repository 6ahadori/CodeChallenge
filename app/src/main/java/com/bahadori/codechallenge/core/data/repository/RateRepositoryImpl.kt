package com.bahadori.codechallenge.core.data.repository

import com.bahadori.codechallenge.core.data.model.toExternal
import com.bahadori.codechallenge.core.domain.repository.RateRepository
import com.bahadori.codechallenge.core.model.Rate
import com.bahadori.codechallenge.core.network.retrofit.RetrofitNextopNetwork
import javax.inject.Inject

class RateRepositoryImpl @Inject constructor(
    private val api: RetrofitNextopNetwork
) : RateRepository {

    override suspend fun getRates(): List<Rate> = api.getRates().rates.toExternal()
}