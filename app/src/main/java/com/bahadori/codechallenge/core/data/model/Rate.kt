package com.bahadori.codechallenge.core.data.model

import com.bahadori.codechallenge.core.model.Rate
import com.bahadori.codechallenge.core.network.model.RateDto

fun RateDto.toExternal(): Rate = Rate(
    symbol = symbol ?: "",
    price = price ?: 0.0
)


fun List<RateDto>.toExternal() = map { it.toExternal() }