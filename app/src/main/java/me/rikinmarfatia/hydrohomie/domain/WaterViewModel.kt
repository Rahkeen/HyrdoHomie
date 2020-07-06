package me.rikinmarfatia.hydrohomie.domain

import androidx.lifecycle.ViewModel
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.subjects.BehaviorSubject
import me.rikinmarfatia.hydrohomie.domain.WaterAction.Add
import me.rikinmarfatia.hydrohomie.domain.WaterAction.Reset
import me.rikinmarfatia.hydrohomie.effects.WaterBroadcaster

class WaterViewModel(
    private val store: WaterKrate,
    private val broadcaster: WaterBroadcaster
) : ViewModel() {
    var state = store.toWaterState()
        private set
    private val stateSubject = BehaviorSubject.createDefault(state)

    fun states(): Observable<WaterState> {
        return stateSubject
    }

    fun actions(action: WaterAction) {
        when (action) {
            is Add -> {
                val nextCount = minOf(state.count + action.amount, state.goal)
                val nextPercentCompletion = nextCount.toFloat() / state.goal
                val nextTransition = WaterTransition(
                    previous = state.percentCompletion,
                    current = nextPercentCompletion
                )
                state = state.copy(
                    count = nextCount,
                    percentCompletion = nextPercentCompletion,
                    transition = nextTransition
                )
                stateSubject.onNext(state)
                store.count = nextCount
                broadcaster.sendUpdateBroadcast()
            }
            is Reset -> {
                state = WaterState()
                stateSubject.onNext(state)
                store.count = 0
                broadcaster.sendUpdateBroadcast()
            }
        }
    }
}