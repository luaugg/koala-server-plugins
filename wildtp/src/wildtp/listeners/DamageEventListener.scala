package wildtp.listeners

import org.bukkit.World.Environment
import org.bukkit.entity.EntityType
import org.bukkit.event.entity.EntityDamageEvent
import org.bukkit.event.{EventHandler, Listener}
import wildtp.WildTp

// This handles player invulnerability shortly after teleporting.
case class DamageEventListener(wildTpPlugin: WildTp) extends Listener {
  import wildTpPlugin.{cooldownMap, invulnerabilityMillis}

  @EventHandler
  def onDamage(event: EntityDamageEvent): Unit = event match {
    case _ if event.getEntityType != EntityType.PLAYER =>
    case _ => invulnerabilityMillis match {
      case Some(value) =>
        val invulnerabilitySpan = if (event.getEntity.getWorld.getEnvironment == Environment.NETHER) {
          value * 2
        } else {
          value
        }

        cooldownMap.get(event.getEntity.getUniqueId) match {
          case Some(timestamp) if System.currentTimeMillis() - timestamp < invulnerabilitySpan =>
            event.setCancelled(true)

          case _ =>
        }

      case _ =>
    }
  }
}
