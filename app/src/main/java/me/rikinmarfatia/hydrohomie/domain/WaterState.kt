package me.rikinmarfatia.hydrohomie.domain

data class WaterState(
    val goal: Int = 8,
    val count: Int = 0,
    val percentCompletion: Float = count.toFloat() / goal,
    val transition: WaterTransition = WaterTransition()
)

data class WaterTransition(
    val previous: Float = 0F,
    val current: Float = 0F
)

sealed class WaterAction {
    data class Add(val amount: Int) : WaterAction()
    object Reset : WaterAction()
}

data class User(
    val name: String = "Eesha",
    val picture: String? = null
)
