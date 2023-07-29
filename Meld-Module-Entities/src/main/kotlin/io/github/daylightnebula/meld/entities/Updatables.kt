package io.github.daylightnebula.meld.entities

import kotlin.concurrent.thread

interface Updatable {
    fun update(tick: ULong)
}

// tick settings and values
const val UPDATE_TIME_TARGET = 50
var currentTick: ULong = 0u

// updatables stuff
val updatables = mutableListOf<Updatable>()
val updatablesThread = thread {
    // run ticks until stop
    while(true) {
        // get start time
        val startTime = System.currentTimeMillis()

        // update all updatables
        synchronized(updatables) {
            updatables.forEach {
                // if entity, make sure has watchers
                if (it is Entity && it.getWatchers().isEmpty()) return@forEach

                // update
                it.update(currentTick)
            }
        }

        // wait so that tick is as long as the update time target
        val diff = (System.currentTimeMillis() - startTime).coerceAtLeast(0L)
        if (diff < UPDATE_TIME_TARGET) Thread.sleep(UPDATE_TIME_TARGET - diff)
        currentTick++
    }
}