package wildtp

import java.util.UUID

import org.bukkit.configuration.file.FileConfiguration
import org.bukkit.plugin.java.JavaPlugin
import wildtp.executors.WildExecutor
import wildtp.listeners.DamageEventListener

import scala.collection.mutable

class WildTpPlugin extends JavaPlugin {
  import utils.config._

  def cooldownMillis =           "cooldownMillis".resolveOptionalInt
  def invulnerabilityMillis =    "invulnerabilityMillis".resolveOptionalInt
  def teleportBoundary =         "teleportBoundary".resolveInt

  def endTeleportAllowed =       "locations.endTeleportAllowed".resolveBoolean
  def netherTeleportAllowed =    "locations.netherTeleportAllowed".resolveBoolean
  def overworldTeleportAllowed = "locations.overworldTeleportAllowed".resolveBoolean


  private val damageEventListener: DamageEventListener = DamageEventListener(this)
  val cooldownMap: mutable.Map[UUID, Long] = mutable.Map()

  override def onDisable(): Unit = cooldownMap.clear()

  override def onEnable(): Unit = {
    saveDefaultConfig()

    getCommand("wild").setExecutor(WildExecutor(this))

    getServer.getPluginManager.registerEvents(damageEventListener, this)
  }

  implicit def config: FileConfiguration = getConfig
}
