package com.bahadori.codechallenge.feature.rate

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bahadori.codechallenge.core.common.ext.format
import com.bahadori.codechallenge.core.domain.usecase.GetRates
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import java.util.Date
import javax.inject.Inject

@HiltViewModel
class RateViewModel @Inject constructor(
    private val getRates: GetRates
) : ViewModel() {

    private val _state = MutableStateFlow(RateState())
    val state = _state.asStateFlow()

    private val _effect = MutableSharedFlow<UiEffect>()
    val effect = _effect.asSharedFlow()

    init {
        getRateList()
    }

    private fun getRateList() {
        getRates().onEach { result ->
            if (result.isSuccess) {
                _state.update {
                    it.copy(
                        rates = result.getOrDefault(emptyList()),
                        date = Date().format(),
                        loading = false
                    )
                }
            } else {
                _effect.emit(
                    UiEffect.ShowMessage(
                        result.exceptionOrNull()?.message ?: "Unexpected error!"
                    )
                )
            }
        }.launchIn(viewModelScope)
    }

    sealed class UiEffect {
        data class ShowMessage(val message: String) : UiEffect()
    }
}