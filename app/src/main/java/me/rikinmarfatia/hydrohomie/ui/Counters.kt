package me.rikinmarfatia.hydrohomie.ui

import androidx.compose.Composable
import androidx.compose.getValue
import androidx.compose.setValue
import androidx.compose.state
import androidx.ui.core.Alignment
import androidx.ui.core.Modifier
import androidx.ui.foundation.Box
import androidx.ui.foundation.ContentGravity
import androidx.ui.foundation.Text
import androidx.ui.layout.Column
import androidx.ui.layout.Row
import androidx.ui.layout.fillMaxWidth
import androidx.ui.layout.padding
import androidx.ui.material.Button
import androidx.ui.material.MaterialTheme
import androidx.ui.text.style.TextAlign
import androidx.ui.unit.dp
import me.rikinmarfatia.hydrohomie.theme.typography

data class CounterState(
    val count: Int = 0
)

@Composable
fun Counter() {
    var counterState by state { CounterState() }
    Box(
        modifier = Modifier.fillMaxWidth(),
        gravity = ContentGravity.Center,
        backgroundColor = MaterialTheme.colors.background
    ) {
        Column {
            Text(
                text = "${counterState.count}",
                modifier = Modifier.fillMaxWidth(),
                style = typography.h1.copy(color = MaterialTheme.colors.onSurface),
                textAlign = TextAlign.Center
            )

            Row(modifier = Modifier.gravity(Alignment.CenterHorizontally)) {
                Button(
                    onClick = { counterState = counterState.copy(count = counterState.count + 1) },
                    modifier = Modifier.padding(8.dp)
                ) {
                    Text(text = "Increment")
                }

                Button(
                    onClick = { counterState = counterState.copy(count = counterState.count - 1) },
                    modifier = Modifier.padding(8.dp)
                ) {
                    Text(text = "Decrement")
                }
            }
        }
    }
}
