package io.github.daylightnebula.meld.server.registries

import java.util.function.Supplier

class SimpleRegistry<M>(
    mappings: M
): Registry<M>(mappings) {
    companion object {
        fun <I: Any, M: Any> fromLoader(input: I, loader: RegistryLoader<I, M>) =
                SimpleRegistry(loader.load(input))

        fun <I: Any, M: Any> create(input: I, registryLoader: Supplier<RegistryLoader<I, M>>): SimpleRegistry<M> {
            return fromLoader(input, registryLoader.get())
        }

        fun <I: Any, M: Any> create(input: I, registryLoader: RegistryLoader<I, M>): SimpleRegistry<M> =
            fromLoader(input, registryLoader)
    }
}