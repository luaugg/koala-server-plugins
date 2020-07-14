# WildernessTeleport
[Minecraft](https://minecraft.net/) plugin,
originally targeted to [PaperMC](https://papermc.io/) 1.16.1. It has one command - `/wild` -
which teleports the player to a random location in any of the three dimensions.

# Configuration and Documentation
WildernessTeleport is highly configurable. Here are the configuration options with
comments explaining what they do:
```yaml
# The minimum amount of time, in milliseconds, a player must wait in between command incovations.
# Defaults to: 10 minutes.
cooldownMillis: 600000

# The amount of time, in milliseconds, a player is invulnerable to all damage after teleporting.
# Note that this value is doubled when teleporting through the Nether.
# Defaults to: 10 seconds.
invulnerabilityMillis: 10000

# The maximum radius for teleports.
# If the boundary is, say, 1000, X and Z co-ordinates will be limited to the range -1000 to +1000.
# Defaults to: 29,999,984 (or 30,000,000 - 16).
teleportBoundary: 29999984

# Whether or not wilderness teleportation is allowed in the Overworld.
# Defaults to: true.
overworldTeleportAllowed: true

# Whether or not wilderness teleportation is allowed in the Nether.
# Defaults to: false.
netherTeleportAllowed: false

# Whether or not wilderness teleportation is allowed in The End.
# Defaults to: false.
endTeleportAllowed: false
```

# Development Details
WildernessTeleport is written in [Scala](https://scala-lang.org) and uses the [Mill](https://github.com/lihaoyi/mill) build tool.
It targets [PaperMC](https://papermc.io) 1.16.1. Older versions of [Minecraft](https://minecraft.net/)
will be supported later, same for [Bukkit](https://dev.bukkit.org/).

To generate a distribution, install Mill first, then run `mill wildtp.assembly`.
A distribution will be built in `out/wildtp/assembly/dest/out.jar`.

Pre-built, slimmer releases will be made soon.
