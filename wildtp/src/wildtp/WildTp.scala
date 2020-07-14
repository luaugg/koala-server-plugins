package wildtp

import java.util.UUID

import org.bukkit.event.HandlerList
import org.bukkit.plugin.java.JavaPlugin
import wildtp.executors.WildExecutor
import wildtp.listeners.DamageEventListener

import scala.collection.mutable

case class WildTp() extends JavaPlugin {
  var cooldownMillis: Option[Int] = _
  var teleportBoundary: Int = _
  var endTeleportAllowed: Boolean = _
  var netherTeleportAllowed: Boolean = _
  var overworldTeleportAllowed: Boolean = _
  var invulnerabilityMillis: Option[Int] = _

  private val damageEventListener: DamageEventListener = DamageEventListener(this)
  val cooldownMap: mutable.Map[UUID, Long] = mutable.Map()

  override def onDisable(): Unit = {
    // Set the executor to null. No more command invocations.
    getCommand("wild").setExecutor(null)

    // Unregister damage listener.
    HandlerList.unregisterAll(this)

    // Clear the cooldown collection.
    cooldownMap.clear()
  }

  override def onEnable(): Unit = {
    // Save a copy of the default configuration, if no con
    // fig is present.
    saveDefaultConfig()

    cooldownMillis = getConfig.getInt("cooldownMillis") match {
      case 0 | -1 => None
      case value => Some(value)
    }

    invulnerabilityMillis = getConfig.getInt("invulnerabilityMillis") match {
      case 0 | -1 => None
      case value => Some(value)
    }

    teleportBoundary = getConfig.getInt("teleportBoundary")
    endTeleportAllowed = getConfig.getBoolean("endTeleportAllowed")
    netherTeleportAllowed = getConfig.getBoolean("netherTeleportAllowed")
    overworldTeleportAllowed = getConfig.getBoolean("overworldTeleportAllowed")

    getCommand("wild").setExecutor(WildExecutor(this))

    // Register invulnerability listener.
    getServer.getPluginManager.registerEvents(damageEventListener, this)
  }
}
