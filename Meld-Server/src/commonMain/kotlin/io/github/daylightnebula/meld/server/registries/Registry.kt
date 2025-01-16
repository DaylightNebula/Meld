package io.github.daylightnebula.meld.server.registries

import java.util.function.Consumer

abstract class Registry<M>(
    var mappings: M
): IRegistry<M> {

    /**
     * Gets the underlying value held by this registry.
     *
     * @return the underlying value held by this registry.
     */
    override fun get(): M {
        return mappings
    }

    /**
     * Sets the underlying value held by this registry.
     * Clears any existing data associated with the previous
     * value.
     *
     * @param mappings the underlying value held by this registry
     */
    override fun set(mappings: M) {
        this.mappings = mappings
    }

    /**
     * Registers what is specified in the given
     * [Consumer] into the underlying value.
     *
     * @param consumer the consumer
     */
    override fun register(consumer: Consumer<M>) {
        mappings?.let { consumer.accept(it) }
    }
}

interface IRegistry<M> {
    fun get(): M
    fun set(mappings: M)
    fun register(consumer: Consumer<M>)
}

interface RegistryLoader<I, O> {
    fun load(input: I): O
}