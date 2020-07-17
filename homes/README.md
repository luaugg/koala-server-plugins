# Homes
Allows a player to have any number of defined home locations that they can teleport to
freely.

# Documentation and Configuration
```yaml
# The minimum amount of time, in milliseconds, a player must wait inbetween teleports.
# Defaults to: 10 seconds.
cooldownMillis: 10000

# The amount of time, in milliseconds, that a player is invincible to all damage after teleporting.
# Defaults to: 5 seconds.
invulnerabilityMillis: 5000

homes:
  # The maximum amount of homes a player is allowed to possess.
  # Note that this limit is global and counts homes in other dimensions.
  # Defaults to: 3.
  maximumNumberOfHomes: 3

  # Whether or not a player is allowed to delete their own homes.
  # Defaults to: true.
  allowDeletingHomes: true
  
  # Whether or not a player is allowed to change the location of one of their
  # - previously-defined homes.
  # Defaults to: true.
  allowOverridingHomes: true
  
  allowedLocations:
    # Whether or not a player is allowed to set a home in the Overworld.
    # Defaults to: true.
    overworldHomes: true

    # Whether or not a player is allowed to set a home in the Nether.
    # Defaults to: false.
    netherHomes: false

    # Whether or not a player is allowed to set a home in The End.
    # Defaults to: false.
    endHomes: false
    
  naming:
    # Whether or not a player is allowed to assign a name to a home,
    # - rather than simple numeric identifiers.
    # Note that the numeric identifiers will be preserved, in addition to any names.
    # Defaults to: true.
    allowNamingHomes: true

    # The maximum amount of characters a home's name can have, if naming homes is enabled.
    # Defaults to: 20.
    homeNameCharLimit: 20
```

This plugin has three supported permissions, all of which are self-explanatory:
* `Homes.home`
* `Homes.sethome`
* `Homes.deletehome`