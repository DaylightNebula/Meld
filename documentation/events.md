# Events
Events are a crucial feature for inter-module communication.  These allow a module to say "hey this happened" and another module or even the same module to say "ok Im going to do stuff with this information".

## Creating an Event
Creating an event is very easy.  It simply acts as a container class for data that implements `Event`.  One can be created like this:

```kotlin
class ExampleEvent(
    val a: String,
    val b: Int
): Event
```

## Broadcasting an Event
When an event is created, it must be broadcast so that modules can listen for that event.  Doing this is very simple, just call the following:

```kotlin
EventBus.callEvent(event)
```

## Listening for an Event
Now for the fun part, listening for those fun events.  First you need to create a class that implements `EventListener` like so:

```kotlin
class ExampleListener: EventListener {

}
```

Next you need to create a function that takes in the event you want to listen for as an argument.  That function must also be marked with an `EventHandler` annotation.  You can write as many `EventHandler`'s as you like in one class.  You can do that like this:

```kotlin
class ExampleListener: EventListener {
    @EventHandler
    fun onExampleEvent(event: ExampleEvent) {
        println("Received $event")
    }
}
```

This function will automatically be called when a `ExampleEvent` is broadcast.