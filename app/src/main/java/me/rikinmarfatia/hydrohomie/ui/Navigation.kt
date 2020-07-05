package me.rikinmarfatia.hydrohomie.ui

sealed class Routing {
    object Daily : Routing()
    object History : Routing()
}