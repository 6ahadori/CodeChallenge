package com.bahadori.codechallenge.core.domain.repository

import com.bahadori.codechallenge.core.model.Rate

interface RateRepository {

    suspend fun getRates(): List<Rate>
}