package io.github.daylightnebula.meld.server.events

import io.github.daylightnebula.meld.server.VarIntCodec
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

data class EventExecutor<D: Event.Data<E>, E: Event>(
    val data: D,
    val executor: (E) -> Unit
)


interface EventListener {
    val executors: List<EventExecutor<*, *>>
}

@OptIn(ExperimentalUuidApi::class)
interface Event {
    interface Data<E: Event> {
        val ID: Uuid
        val executors: MutableList<(E) -> Unit>

        fun <D: Event.Data<O>, O: Event> add(executor: EventExecutor<D, O>) =
            if (executor.data == this) executors.add(executor.executor)
            else Unit

        fun execute(e: Event) =
            executors.forEach { it(e as E) }
    }

    val ID: Uuid
}

interface CancellableEvent: Event {
    var cancelled: Boolean
}

@OptIn(ExperimentalUuidApi::class)
object EventBus {
    private val listenerMap = hashMapOf<Uuid, Event.Data<*>>()

    // function to register event listeners event handler functions
    fun register(listener: EventListener) {
        listener.executors.forEach { executor ->
            // get data
            var data = listenerMap[executor.data.ID]
            if (data == null) {
                data = executor.data
                listenerMap[executor.data.ID] = data
            }

            // add execution functions
            data.add(executor)
        }

        println("Registered event listener: $listener")
    }

    // function to execute all events handlers for an event
    fun <E: Event> callEvent(event: E) = listenerMap[event.ID]?.execute(event)
}
