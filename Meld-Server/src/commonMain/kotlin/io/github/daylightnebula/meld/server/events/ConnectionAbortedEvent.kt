package io.github.daylightnebula.meld.server.events

import io.github.daylightnebula.meld.server.networking.common.IConnection
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

@OptIn(ExperimentalUuidApi::class)
data class ConnectionAbortedEvent(val connection: IConnection<*>): Event {
    companion object: Event.Data<ConnectionAbortedEvent> {
        override val ID: Uuid = Uuid.Companion.random()
        override val executors: MutableList<(ConnectionAbortedEvent) -> Unit> = mutableListOf()
    }

    override val ID: Uuid = Companion.ID
}