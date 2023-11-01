package com.bahadori.codechallenge.feature.rate

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.bahadori.codechallenge.R
import com.bahadori.codechallenge.core.designsystem.theme.Grey
import com.bahadori.codechallenge.core.model.Rate
import com.bahadori.codechallenge.core.model.State
import com.bahadori.codechallenge.feature.rate.components.RateView
import kotlinx.coroutines.flow.collectLatest


@Composable
fun RateRoute(
    onShowSnackbar: suspend (String, String?) -> Boolean,
    viewModel: RateViewModel = hiltViewModel()
) {
    val state = viewModel.state.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.effect.collectLatest { uiEffect ->
            when (uiEffect) {
                is RateViewModel.UiEffect.ShowMessage -> {
                    onShowSnackbar(uiEffect.message, null)
                }
            }
        }
    }

    RateScreen(
        state = state.value
    )
}

@Composable
fun RateScreen(
    state: RateState
) {
    Box {
        Column(modifier = Modifier.fillMaxWidth()) {
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(32.dp, 32.dp, 32.dp, 8.dp),
                text = stringResource(id = R.string.title),
                style = MaterialTheme.typography.headlineMedium
            )
            LazyColumn(modifier = Modifier.fillMaxSize()) {
                items(state.rates) { rate ->
                    RateView(value = rate)
                }
                item {
                    if (state.date != "")
                        Text(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(32.dp),
                            text = stringResource(id = R.string.latest_update, state.date),
                            style = MaterialTheme.typography.labelMedium.copy(
                                color = Grey,
                                textAlign = TextAlign.Center
                            )
                        )
                }
            }
        }
        if (state.loading) {
            CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
        }
    }

}

@Preview
@Composable
fun RateScreenPreview() {
    RateScreen(
        RateState(
            rates = listOf(
                Rate("USD", 40.0, State.Increased),
                Rate("EUR", 30.0, State.Decreased),
                Rate("IR", 20.0, State.Decreased),
            ),
            date = "30/03/2023 - 16:00"
        )
    )
}