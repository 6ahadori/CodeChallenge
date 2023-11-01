package com.bahadori.codechallenge.feature.rate.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.graphics.drawable.toBitmapOrNull
import com.bahadori.codechallenge.R
import com.bahadori.codechallenge.core.common.ext.getDrawable
import com.bahadori.codechallenge.core.designsystem.theme.CodeChallengeTheme
import com.bahadori.codechallenge.core.designsystem.theme.Green
import com.bahadori.codechallenge.core.designsystem.theme.Red
import com.bahadori.codechallenge.core.model.Rate
import com.bahadori.codechallenge.core.model.State


@Composable
fun RateView(value: Rate) {
    val context = LocalContext.current

    Card(
        modifier = Modifier.padding(32.dp, 8.dp),
        shape = RoundedCornerShape(16.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(70.dp)
                .padding(16.dp, 8.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                val bitmap =
                    context.getDrawable(value.symbol.lowercase())?.toBitmapOrNull()?.asImageBitmap()
                bitmap?.let {
                    Image(
                        bitmap = it,
                        contentDescription = "Pair icon"
                    )
                }
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = value.symbol,
                    color = Color.Black,
                    style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
                )
            }
            Row(verticalAlignment = Alignment.CenterVertically) {
                val price = "%.4f".format(value.price)
                val style = MaterialTheme.typography.titleLarge
                when (value.state) {
                    State.Decreased -> {
                        Text(text = price, color = Red, style = style)
                        Spacer(modifier = Modifier.width(4.dp))
                        Icon(
                            modifier = Modifier.size(20.dp),
                            painter = painterResource(id = R.drawable.ic_arrow_downward),
                            contentDescription = "Downward icon",
                            tint = Red
                        )
                    }

                    State.Increased -> {
                        Text(text = price, color = Green, style = style)
                        Spacer(modifier = Modifier.width(4.dp))
                        Icon(
                            modifier = Modifier.size(20.dp),
                            painter = painterResource(id = R.drawable.ic_arrow_upward),
                            contentDescription = "Upward icon",
                            tint = Green
                        )
                    }
                }
            }
        }
    }
}


@Preview
@Composable
fun RateViewPreview() {
    CodeChallengeTheme {
        RateView(value = Rate("USD", 40.0))
    }
}