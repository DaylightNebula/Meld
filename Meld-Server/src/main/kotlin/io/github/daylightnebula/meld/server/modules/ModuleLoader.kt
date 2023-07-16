package io.github.daylightnebula.meld.server.modules

import org.json.JSONObject
import java.io.File
import java.net.URLClassLoader

object ModuleLoader {
    val modules = mutableListOf<MeldModule>()
    private val modulesFolder = File("modules")

    fun load() {
        // load all jar files
        modulesFolder.listFiles()?.forEach {
            // only jar files
            if (it.extension != "jar") return@forEach

            // load the jar file
            loadJar(it)
        }
    }

    private fun loadJar(jar: File) {
        println("Loading module ${jar.name}...")
        // get loader
        val loader = URLClassLoader(arrayOf(jar.toURI().toURL()))

        // get config file
        val config = JSONObject(loader.getResource("module.json")?.readText() ?: "{}")
        val mainClass = config.optString("main")
        if (mainClass == null) {
            println("WARN failed to load module ${jar.name}")
            return
        }

        // get main class and its constructor
        val constructor = Class.forName(mainClass, true, loader).getDeclaredConstructor()
        constructor.trySetAccessible()

        // create new module
        val module = constructor.newInstance() as? MeldModule
        if (module == null) {
            println("WARN failed to initialize module ${jar.name}")
            return
        }

        // save
        modules.add(module)

        // enable the module
        module.onEnable()
        println("Loaded module ${jar.name}")
    }
}

interface MeldModule {
    fun onEnable()
    fun onDisable()
}