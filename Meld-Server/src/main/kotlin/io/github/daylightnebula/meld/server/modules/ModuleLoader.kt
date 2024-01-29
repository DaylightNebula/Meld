package io.github.daylightnebula.meld.server.modules

import org.json.JSONObject
import java.io.File
import java.net.URLClassLoader
import java.util.jar.JarFile

object ModuleLoader {
    val modules = mutableListOf<MeldModule>()
    private val modulesFolder = File("modules")

    fun load() {
        // get a list of all jar files to load
        val jarFiles = modulesFolder.listFiles()!!
            .filter { it.extension == "jar" }

        // create a loader
        val loader = URLClassLoader(jarFiles.map { it.toURI().toURL() }.toTypedArray())

        // initialize each module
        for (jarFile in jarFiles) {
            println("Loading module ${jarFile.name}...")
            // get config file
            val config = JarFile(jarFile).use {
                val entry = it.getEntry("module.json")
                JSONObject(it.getInputStream(entry).bufferedReader().readText())
            }
            val mainClass = config.optString("main")
            if (mainClass == null) {
                println("WARN failed to load module ${jarFile.name}")
                return
            }

            // get main class and its constructor
            val constructor = Class.forName(mainClass, true, loader).getDeclaredConstructor()
//            constructor.trySetAccessible()

            // create new module
            val module = constructor.newInstance() as? MeldModule
            if (module == null) {
                println("WARN failed to initialize module ${jarFile.name}")
                return
            }

            // save
            modules.add(module)

            // enable the module
            module.onEnable()
            println("Loaded module ${jarFile.name}")
        }
    }
}

interface MeldModule {
    fun onEnable()
    fun onDisable()
}