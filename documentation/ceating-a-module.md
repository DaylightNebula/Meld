# Creating a Meld Module
Modules are a core part of Meld.  They implement all functionality of Meld.  Modules handle everything from logging into the server, the world, the player state, etc.

## Implementing a Module
To create a module, you must have a class that is used to create, enable and disable modules.  This serves as the entrypoint for a module.  To do this, you must have a class that implements `MeldModule` as shown below.

```kotlin
class ExampleModule: MeldModule {
    override fun onEnable() {}
    override fun onDisable() {}
}
```

## Loading the Module
You created your first module, amazing!.  But the Meld Server does not know what to do with it.  To tell the server, you need to create a file named `module.json` in the resources directory of your module.  This JSON file needs to specify the path to the main file created above as shown below.

```json
{
    "main": "com.example.ExampleModule"
}
```

This file tells Meld to load ExampleModule as the modules main file.