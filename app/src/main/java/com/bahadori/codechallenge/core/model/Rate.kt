package com.bahadori.codechallenge.core.model

data class Rate(
    val symbol: String,
    val price: Double,
    val state: State = State.Increased
)

sealed interface State {
    data object Increased : State
    data object Decreased : State
}

