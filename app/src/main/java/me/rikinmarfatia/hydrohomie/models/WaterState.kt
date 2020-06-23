package me.rikinmarfatia.hydrohomie.models

data class WaterState(
    val goal: Int = 8,
    val count: Int = 0,
    val transition: WaterTransition = WaterTransition()
)

data class WaterTransition(
    val previous: Float = 0F,
    val current: Float = 0F
)
