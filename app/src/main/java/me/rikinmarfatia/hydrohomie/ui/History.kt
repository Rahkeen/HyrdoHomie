package me.rikinmarfatia.hydrohomie.ui


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.ConstraintSet
import androidx.constraintlayout.compose.Dimension
import me.rikinmarfatia.hydrohomie.R
import me.rikinmarfatia.hydrohomie.domain.WaterState
import me.rikinmarfatia.hydrohomie.theme.HydroHomieTheme
import me.rikinmarfatia.hydrohomie.theme.hydroBlue

data class HistoryState(
    val days: List<WaterState>
)

@Composable
fun HistoryContainer(state: HistoryState) {
    HydroHomieTheme {
        LazyColumn(
            content = {
                items(items = state.days) { item ->
                    Spacer(modifier = Modifier.height(8.dp))
                    HistoryRow(state = item)
                }
            }
        )
    }
}

@ExperimentalStdlibApi
fun dummyHistoryState(num: Int): HistoryState {
    return HistoryState(
        days = buildList {
            for (i in 0 until num) {
                add(WaterState(count = i % 9))
            }
        }
    )
}

@Composable
fun HistoryRow(state: WaterState) {

    val cardModifier = Modifier
        .fillMaxWidth()
        .padding(horizontal = 8.dp)
        .height(60.dp)

    val waterIcon = painterResource(id = R.drawable.ic_favorite)

    Card(
        modifier = cardModifier,
        shape = RoundedCornerShape(8.dp),
        elevation = 4.dp
    ) {
        ConstraintLayout(
            ConstraintSet {
                val date = createRefFor("date")
                val progress = createRefFor("progress")
                val icon = createRefFor("icon")
                val fill = createRefFor("fill")
                val guideline = createGuidelineFromStart(state.percentCompletion)

                constrain(fill) {
                    start.linkTo(parent.start)
                    top.linkTo(parent.top)
                    bottom.linkTo(parent.bottom)
                    end.linkTo(guideline)
                    width = Dimension.fillToConstraints
                    height = Dimension.fillToConstraints
                }

                constrain(icon) {
                    centerVerticallyTo(parent)
                    start.linkTo(parent.start, 16.dp)
                }

                constrain(date) {
                    centerVerticallyTo(parent)
                    start.linkTo(icon.end, 16.dp)
                }

                constrain(progress) {
                    centerVerticallyTo(parent)
                    end.linkTo(parent.end, 16.dp)
                }

            }
        ) {

            Box(
                modifier = Modifier.background(color = hydroBlue).layoutId("fill")
            )
            Icon(
                painter = waterIcon,
                contentDescription = null,
                tint = MaterialTheme.colors.onSurface,
                modifier = Modifier.layoutId("icon")
            )
            Text(
                text = "Saturday, January 8th, 2020",
                modifier = Modifier.layoutId("date")
            )
            Text(
                text = "${state.count} / ${state.goal}",
                modifier = Modifier.layoutId("progress")
            )
        }
    }
}

@ExperimentalStdlibApi
@Preview(showBackground = true)
@Composable
fun HistoryRowPreview() {
    HistoryContainer(
        state = dummyHistoryState(20)
    )
}
