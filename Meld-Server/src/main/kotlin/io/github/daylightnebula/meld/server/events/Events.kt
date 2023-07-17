package io.github.daylightnebula.meld.server.events

import io.ktor.util.reflect.*
import kotlin.reflect.KClass
import kotlin.reflect.KFunction
import kotlin.reflect.KParameter
import kotlin.reflect.KType
import kotlin.reflect.full.*

annotation class EventHandler
interface EventListener
interface Event

object EventBus {
    private val listenerMap = hashMapOf<KType, MutableList<Pair<EventListener, KFunction<*>>>>()

    // function to register event listeners event handler functions
    fun register(listener: EventListener) {
        // load all event handler functions from the given listener
        listener::class.declaredMemberFunctions
            .filter { it.findAnnotation<EventHandler>() != null }
            .forEach { func ->
                // only 1 parameter
                if (func.valueParameters.size != 1) return@forEach
                val param = func.valueParameters.first()

                // make sure that parameter is an event
                if (!checkParameterInheritance(param, Event::class)) return@forEach

                // get list of functions for the given param type
                var list = listenerMap[param.type]
                if (list == null) {
                    list = mutableListOf()
                    listenerMap[param.type] = list
                }

                // save event
                list.add(listener to func)
            }

        println("Registered event listener: $listener")
    }

    // function to execute all events handlers for an event
    fun callEvent(event: Event) =
        listenerMap[event::class.starProjectedType]?.forEach { it.second.call(it.first, event) }
            ?: println("WARN no event listeners registered for event ${event.javaClass}")

    // function to check if a parameter inherits from the given class
    private fun checkParameterInheritance(parameter: KParameter, className: KClass<*>) =
        parameter.type.classifier?.let { it as? KClass<*> }?.isSubclassOf(className) ?: false
}
