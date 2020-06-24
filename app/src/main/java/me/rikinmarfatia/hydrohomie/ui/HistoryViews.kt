package me.rikinmarfatia.hydrohomie.ui

import androidx.compose.Composable
import androidx.ui.core.Modifier
import androidx.ui.core.tag
import androidx.ui.foundation.Icon
import androidx.ui.foundation.Image
import androidx.ui.foundation.Text
import androidx.ui.foundation.shape.corner.RoundedCornerShape
import androidx.ui.graphics.Color
import androidx.ui.graphics.vector.drawVector
import androidx.ui.layout.Column
import androidx.ui.layout.ConstraintLayout
import androidx.ui.layout.ConstraintSet
import androidx.ui.layout.Spacer
import androidx.ui.layout.fillMaxHeight
import androidx.ui.layout.fillMaxWidth
import androidx.ui.layout.height
import androidx.ui.layout.padding
import androidx.ui.layout.preferredHeight
import androidx.ui.layout.preferredWidth
import androidx.ui.material.Card
import androidx.ui.material.ListItem
import androidx.ui.material.MaterialTheme
import androidx.ui.res.vectorResource
import androidx.ui.tooling.preview.Preview
import androidx.ui.unit.dp
import me.rikinmarfatia.hydrohomie.R
import me.rikinmarfatia.hydrohomie.models.WaterState
import me.rikinmarfatia.hydrohomie.theme.hydroBlue

data class HistoryState(
    val days: List<WaterState>
)

@Composable
fun HistoryContainer(state: WaterState) {
    Column(
        modifier = Modifier
        .padding(8.dp)
        .fillMaxHeight()
    ) {
        HistoryRow(state)
        Spacer(modifier = Modifier.height(8.dp))
        HistoryRow(state)
    }
}

@Composable
fun HistoryRow(state: WaterState) {

    val constraints = ConstraintSet {
        val date = tag("date")
        val progress = tag("progress")
        val icon = tag("icon")

        icon.apply {
            centerVertically()
            left constrainTo parent.left
            left.margin = 16.dp
        }

        date.apply {
            centerVertically()
            left constrainTo icon.right
            left.margin = 16.dp
        }

        progress.apply {
            centerVertically()
            right constrainTo parent.right
            right.margin = 16.dp
        }
    }

    val cardModifier = Modifier
        .fillMaxWidth()
        .height(60.dp)

    val waterIcon = vectorResource(id = R.drawable.ic_favorite)

    Card(
        modifier = cardModifier,
        shape = RoundedCornerShape(8.dp),
        elevation = 4.dp
    ) {
        ConstraintLayout(constraints) {
            Icon(
                asset = waterIcon,
                tint = hydroBlue,
                modifier = Modifier.tag("icon")
            )
            Text("Saturday, January 8th, 2020", modifier = Modifier.tag("date"))
            Text("${state.count} / ${state.goal}", modifier = Modifier.tag("progress"))
        }
    }
}

@Preview(showBackground = true)
@Composable
fun HistoryRowPreview() {
    HistoryContainer(state = WaterState())
}
