package com.bahadori.codechallenge.core.network.model

import kotlinx.serialization.Serializable

@Serializable
data class RatesDto(
    val rates: List<RateDto>
)

@Serializable
data class RateDto(
    val symbol: String?,
    val price: Double?,
)
