Android Configuration Manager (ACM) – *Deprecated* 

This project is deprecated and no longer actively maintained.** However, if you're nostalgic for the days of Android development with simplified configuration management, this README will help you understand how it worked back when it was relevant

The **Android Configuration Manager (ACM)** was a lightweight library designed to simplify app configuration storage in Android. Instead of dealing with complex **SQLite**, **Shared Preferences**, or **file-based persistence**, ACM allowed developers to **store configuration settings as Java objects** – a sort of **Light Object Mapping (LOM)** for Android. ✨

## Key Features

- ✅ **Android Backup Support** – Integrated with Shared Preferences 🔄
- ✅ **Saves Time** – Reduces boilerplate for persisting objects ⏳
- ✅ **Future-Proof** – Fault-tolerant handling of unexpected changes 🛠️
- ✅ **Uses Objects** – No more error-prone key-value pairs! 🏷️
- ✅ **Polymorphism-Friendly** – Works with parent-child config structures 👨‍👩‍👦‍👦

## Quick Start

```java
// Define your configuration class
MyConfig config = new MyConfig();
config.setId("MyConfig");
config.setMyBoolean(true);

// Grab Android SharedPreferences
SharedPreferences sharedPreferences = 
    PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
ConfigManager configManager = new ConfigManager(sharedPreferences);

// Setup a deserializer
SharedPrefenceConfigDeserialiser deserialiser =
    new SharedPrefenceConfigDeserialiser(sharedPreferences, config);
configManager.add(new DefaultConfigDeserializerStrategy(deserialiser));

// Save and retrieve the config
configManager.saveConfig(config);
MyConfig persistedConfig = (MyConfig) configManager.getConfig("MyConfig");

// Print stored value
System.out.println(persistedConfig.getMyBoolean());
```
## How to integrate NZBAir

ACM was widely used in apps like **NZBAir**, where:

- 📌 *Preference Launcher* provided an entry point to different configuration types.
- 📌 *Preference List Activity* managed multiple provider configurations.
- 📌 *Config Activity* generated UI forms for configuration objects automatically.

##  TODO (When ACM Was Active)

- 🔹 Decouple SharedPreferences from ACM (support for SQL/file storage) 🗄️
- 🔹 Improve test coverage for Android 🚀
- 🔹 Use Annotations for defining config types 🎯
- 🔹 Optimize configuration loading 📈
- 🔹 Build a full example project 🏗️

## 🛑 Limitations

- ⚠️ **Only supports primitives** (no complex object graphs!)
- ⚠️ **Type erasure** increases boilerplate code 😖

## 📦 Prerequisites (Back in the Day)

- **ActionBarSherlock** (for Honeycomb & ICS support) 📜

## Where's the Code?

Find the source code here: [GitHub Repository](https://github.com/milesburton/Android-Configuration-Manager) 🏗️
