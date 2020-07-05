package me.rikinmarfatia.hydrohomie.ui

import androidx.compose.Composable
import androidx.ui.core.Modifier
import androidx.ui.foundation.Box
import androidx.ui.foundation.Icon
import androidx.ui.foundation.Text
import androidx.ui.foundation.lazy.LazyColumnItems
import androidx.ui.foundation.shape.corner.RoundedCornerShape
import androidx.ui.layout.ConstraintLayout
import androidx.ui.layout.Dimension
import androidx.ui.layout.Spacer
import androidx.ui.layout.fillMaxWidth
import androidx.ui.layout.height
import androidx.ui.layout.padding
import androidx.ui.material.Card
import androidx.ui.material.MaterialTheme
import androidx.ui.res.vectorResource
import androidx.ui.tooling.preview.Preview
import androidx.ui.unit.dp
import me.rikinmarfatia.hydrohomie.R
import me.rikinmarfatia.hydrohomie.models.WaterState
import me.rikinmarfatia.hydrohomie.theme.HydroHomieTheme
import me.rikinmarfatia.hydrohomie.theme.hydroBlue

data class HistoryState(
    val days: List<WaterState>
)

@Composable
fun HistoryContainer(state: HistoryState) {
    HydroHomieTheme {
        LazyColumnItems(items = state.days,
            itemContent = {
                Spacer(modifier = Modifier.height(8.dp))
                HistoryRow(state = it)
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

    val waterIcon = vectorResource(id = R.drawable.ic_favorite)

    Card(
        modifier = cardModifier,
        shape = RoundedCornerShape(8.dp),
        elevation = 4.dp
    ) {
        ConstraintLayout {
            val (date, progress, icon, fill) = createRefs()
            val guideline = createGuidelineFromStart(state.percentCompletion)
            Box(
                backgroundColor = hydroBlue,
                modifier = Modifier.constrainAs(fill) {
                    start.linkTo(parent.start)
                    top.linkTo(parent.top)
                    bottom.linkTo(parent.bottom)
                    end.linkTo(guideline)
                    width = Dimension.fillToConstraints
                    height = Dimension.fillToConstraints
                }
            )
            Icon(
                asset = waterIcon,
                tint = MaterialTheme.colors.onSurface,
                modifier = Modifier.constrainAs(icon) {
                    centerVerticallyTo(parent)
                    start.linkTo(parent.start, 16.dp)

                }
            )
            Text(
                text = "Saturday, January 8th, 2020",
                modifier = Modifier.constrainAs(date) {
                    centerVerticallyTo(parent)
                    start.linkTo(icon.end, 16.dp)
                }
            )
            Text(
                text = "${state.count} / ${state.goal}",
                modifier = Modifier.constrainAs(progress) {
                    centerVerticallyTo(parent)
                    end.linkTo(parent.end, 16.dp)
                }
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
