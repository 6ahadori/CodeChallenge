package com.bahadori.codechallenge.core.domain.usecase

import com.bahadori.codechallenge.core.domain.repository.RateRepository
import com.bahadori.codechallenge.core.model.Rate
import com.bahadori.codechallenge.core.model.State
import kotlinx.coroutines.currentCoroutineContext
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.channelFlow
import kotlinx.coroutines.isActive
import javax.inject.Inject

class GetRates @Inject constructor(
    private val repository: RateRepository
) {

    private var previousRates = mutableListOf<Rate>()

    operator fun invoke() = channelFlow {
        while (currentCoroutineContext().isActive) {
            try {
                val rates = repository.getRates()
                send(Result.success(rates.map { rate ->
                    val previousValue =
                        previousRates.find { it.symbol == rate.symbol }?.price ?: return@map rate
                    rate.copy(
                        state = when {
                            rate.price < previousValue -> State.Decreased
                            else -> State.Increased
                        }
                    )

                }))
                previousRates = rates.toMutableList()
            } catch (e: Throwable) {
                send(Result.failure(e))
            }
            delay(INTERVAL)
        }
    }

    companion object {
        private const val INTERVAL = 2 * 60 * 1000L
    }
}

