package com.bahadori.codechallenge.feature.rate

import com.bahadori.codechallenge.core.model.Rate


data class RateState(
    val rates: List<Rate> = emptyList(),
    val loading: Boolean = true,
    val date: String = "",
)
