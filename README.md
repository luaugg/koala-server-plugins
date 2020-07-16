# Koala Server Plugins
A collection of open-source, server-independent [Minecraft](https://minecraft.net/)
plugins originally intended for a server I run. All of these plugins are documented,
and will run on any supported Minecraft server.

All of them have been written with [PaperMC](https://papermc.io/) in mind,
specifically version 1.16 — although older versions *should* work,
and even non-Paper servers should too, as long as they run [Bukkit](https://dev.bukkit.org/).

# Documentation
All the documentation you need for each plugin is included in its respective directory.
I am open for contact should any extra information be required.

# Development Details
All the plugins collected here are written in [Scala](https://scala-lang.org/),
using the [Mill](http://www.lihaoyi.com/mill/) build tool, and depend on
Paper's API, which is nearly identical to the Bukkit API.

In the future, the dependency will be Bukkit rather than Paper, as not all
Java Edition servers run Paper, but almost all run Bukkit.

Each directory is itself a Mill module, so to generate a distribution
ready to be dropped into a server's plugin folder, follow these steps:
* Install Mill.
* Clone this repository.
* Run `mill <module name>.assembly`.
* Your assembly will be located in `out/<module namme>/assembly/dest`.
* Copy it over to your Minecraft server's plugins folder.
* Apply any optional configuration.
* Restart/reload your server.
* Done.

If you wish to modify the actual source code, and you use [IntelliJ IDEA](https://www.jetbrains.com/idea/),
you can also run `mill mill.scalalib.GenIdea/Idea`, which will provide Mill
support within IntelliJ.

Slimmer, pre-built releases will be made soon.

# Licensing
All the plugins collected here inherit licensing from upstream projects.
As a result, they are licensed under GPL v3.