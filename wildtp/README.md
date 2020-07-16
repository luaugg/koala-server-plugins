# WildernessTeleport
Allows the player to teleport to a random location in any of the three dimensions,
using the `/wild` command.

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

Using this plugin requires the permission `WildernessTeleport.wild`.
