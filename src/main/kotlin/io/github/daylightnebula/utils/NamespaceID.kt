package io.github.daylightnebula.utils

import com.github.benmanes.caffeine.cache.Cache
import com.github.benmanes.caffeine.cache.Caffeine
import net.kyori.adventure.key.Key
import org.intellij.lang.annotations.Pattern


class NamespaceID private constructor(private val full: String, private val domain: String, private val path: String) :
    CharSequence,
    Key {
    init {
        assert(!domain.contains(".") && !domain.contains("/")) { "Domain cannot contain a dot nor a slash character ($full)" }
        assert(domain.matches(legalLetters.toRegex())) { "Illegal character in domain (" + full + "). Must match " + legalLetters }
        assert(path.matches(legalPathLetters.toRegex())) { "Illegal character in path (" + full + "). Must match " + legalPathLetters }
    }

    fun domain(): String {
        return domain
    }

    fun path(): String {
        return path
    }

    override fun equals(o: Any?): Boolean {
        if (this === o) return true
        return if (o !is Key) false else full == o.asString()
    }

    override fun hashCode(): Int {
        return full.hashCode()
    }

    override val length: Int
        get() = full.length

    override fun get(index: Int): Char {
        return full[index]
    }
//    fun charAt(index: Int): Char = get(index)

    override fun subSequence(start: Int, end: Int): CharSequence {
        return full.subSequence(start, end)
    }

    override fun toString(): String {
        return full
    }

    @Pattern("[a-z0-9_\\-.]+")
    override fun namespace(): String {
        return domain
    }

    override fun value(): String {
        return path
    }

    override fun asString(): String {
        return full
    }

    @Deprecated("")
    fun getDomain(): String {
        return domain()
    }

    @Deprecated("")
    fun getPath(): String {
        return path()
    }

    companion object {
        private const val legalLetters = "[0123456789abcdefghijklmnopqrstuvwxyz_-]+"
        private const val legalPathLetters = "[0123456789abcdefghijklmnopqrstuvwxyz./_-]+"
        private val CACHE: Cache<String, NamespaceID> = Caffeine.newBuilder().weakKeys().weakValues().build()
        fun from(namespace: String): NamespaceID {
            return CACHE[namespace, { id: String ->
                var id = id
                val index = id.indexOf(':')
                val domain: String
                val path: String
                if (index < 0) {
                    domain = "minecraft"
                    path = id
                    id = "minecraft:$id"
                } else {
                    domain = id.substring(0, index)
                    path = id.substring(index + 1)
                }
                NamespaceID(id, domain, path)
            }]
        }

        fun from(domain: String, path: String): NamespaceID {
            return from("$domain:$path")
        }

        fun from(key: Key): NamespaceID {
            return from(key.asString())
        }
    }
}
