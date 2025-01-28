package io.github.daylightnebula.meld.server.entities

import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.internal.synchronized
import kotlinx.coroutines.launch
import kotlinx.datetime.Clock

interface Updatable {
    fun update(tick: ULong)
}

// tick settings and values
const val UPDATE_TIME_TARGET = 50
var currentTick: ULong = 0u

// updatables stuff
val updatables = mutableListOf<Updatable>()
val updatablesThread = GlobalScope.launch {
    // run ticks until stop
    while(true) {
        // get start time
        val startTime = Clock.System.now().toEpochMilliseconds()

        // update all updatables
        updatables.forEach {
            // if entity, make sure has watchers
            if (it is Entity && it.getWatchers().isEmpty()) return@forEach

            // update
            it.update(currentTick)
        }

        // wait so that tick is as long as the update time target
        val diff = (Clock.System.now().toEpochMilliseconds() - startTime).coerceAtLeast(0L)
        if (diff < UPDATE_TIME_TARGET) delay(UPDATE_TIME_TARGET - diff)
        currentTick++
    }
}