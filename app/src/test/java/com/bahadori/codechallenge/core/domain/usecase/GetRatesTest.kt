package com.bahadori.codechallenge.core.domain.usecase

import com.bahadori.codechallenge.core.domain.repository.RateRepository
import com.bahadori.codechallenge.core.model.Rate
import com.bahadori.codechallenge.core.model.State
import com.google.common.truth.Truth.assertThat
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class GetRatesTest {

    private val rateRepository = mockk<RateRepository>()

    @Before
    fun setup() {
        MockKAnnotations.init(this)
    }

    @Test
    fun shouldChangeRateStateInDifferentPriceValues() = runTest() {
        val getRate = GetRates(rateRepository)

        val initialRates = listOf(
            Rate("A", 100.0),
            Rate("B", 120.0),
            Rate("C", 80.0)
        )

        val updatedRates = listOf(
            Rate("B", 110.0),
            Rate("A", 115.0),
            Rate("C", 80.0)
        )

        // Mock the repository to return the initial rates and then the updated rates
        coEvery { rateRepository.getRates() } returnsMany listOf(initialRates, updatedRates)

        val result = getRate().take(2).toList()

        val expectedResult = listOf(
            Result.success(
                listOf(
                    Rate("A", 100.0),
                    Rate("B", 120.0),
                    Rate("C", 80.0)
                )
            ),
            Result.success(
                listOf(
                    Rate("B", 110.0, State.Decreased),
                    Rate("A", 115.0, State.Increased),
                    Rate("C", 80.0, State.Increased)
                )
            )
        )

        assertThat(result).isEqualTo(expectedResult)
    }

    @Test
    fun shouldReturnIncreasedStateInNewPairs() = runTest() {
        val getRate = GetRates(rateRepository)

        val initialRates = listOf(
            Rate("A", 100.0),
            Rate("B", 120.0),
            Rate("C", 80.0)
        )

        val updatedRates = listOf(
            Rate("B", 110.0),
            Rate("A", 115.0),
            Rate("C", 80.0),
            Rate("D", 80.0),
        )

        // Mock the repository to return the initial rates and then the updated rates
        coEvery { rateRepository.getRates() } returnsMany listOf(initialRates, updatedRates)

        val result = getRate().take(2).toList()[1].getOrNull()?.get(3)?.state

        val expectedResult = State.Increased

        assertThat(result).isEqualTo(expectedResult)
    }

    @Test
    fun shouldThrowExceptionWhenApiDoesNotRespond() = runTest {
        val getRates = GetRates(rateRepository)

        coEvery { rateRepository.getRates() } throws Exception()

        val result = getRates().first()

        assertThat(result.exceptionOrNull()).isInstanceOf(Throwable::class.java)
    }
}