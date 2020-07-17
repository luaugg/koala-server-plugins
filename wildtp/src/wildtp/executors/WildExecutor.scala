package wildtp.executors

import org.bukkit.{ChatColor, Location}
import org.bukkit.World.Environment
import org.bukkit.command.{Command, CommandExecutor, CommandSender}
import org.bukkit.entity.Player
import wildtp.WildTpPlugin

import scala.concurrent.duration.{MILLISECONDS => Milliseconds}
import scala.util.{Random => random}

case class WildExecutor(plugin: WildTpPlugin) extends CommandExecutor {
  override def onCommand(sender: CommandSender, command: Command, label: String, args: Array[String]): Boolean = {
    import plugin._

    def millisecondsToFriendlyString(milliseconds: Long): String = milliseconds match {
      case _ if milliseconds < 60000 => f"${milliseconds / 1000f}%.2fs"
      case _ if milliseconds < 3600000 =>
        val minutes = Milliseconds.toMinutes(milliseconds)
        val seconds = Milliseconds.toSeconds(milliseconds) % 60
        s"$minutes:$seconds"

      case _ if milliseconds < 86400000 =>
        val hours = Milliseconds.toHours(milliseconds)
        val minutes = Milliseconds.toMinutes(milliseconds) % 60
        val seconds = Milliseconds.toSeconds(milliseconds) % 60
        s"$hours:$minutes:$seconds"

      case _ => "Over a day"
    }

    val player = sender match {
      case asPlayer: Player => asPlayer
      case _ =>
        sender.sendMessage(s"${ChatColor.RED}[WildernessTeleport]: This command can only be used by a logged-in player.")
        return false
    }

    val environment = player.getWorld.getEnvironment
    environment match {
      case Environment.NORMAL if !overworldTeleportAllowed =>
        player.sendMessage(s"${ChatColor.RED}[WildernessTeleport]: Overworld wilderness teleportation has been disabled.")
        return false

      case Environment.NETHER if !netherTeleportAllowed =>
        player.sendMessage(s"${ChatColor.RED}[WildernessTeleport]: Nether wilderness teleportation has been disabled.")
        return false

      case Environment.THE_END if !endTeleportAllowed =>
        player.sendMessage(s"${ChatColor.RED}[WildernessTeleport]: End wilderness teleportation has been disabled.")
        return false

      case _ =>
    }

    cooldownMillis match {
      case Some(cooldown) => cooldownMap.get(player.getUniqueId) match {
        case Some(timestamp) if System.currentTimeMillis() - timestamp < cooldown =>
          val timeToWait = millisecondsToFriendlyString(cooldown - (System.currentTimeMillis() - timestamp))
          player.sendMessage(s"${ChatColor.RED}[WildernessTeleport]: Chill for a bit. ${ChatColor.BLUE}$timeToWait${ChatColor.RED} remaining.")
          return false

        case _ =>
      }

      case None =>
    }

    def getTeleportLocation: Location = {
      val playerWorld = player.getWorld
      import playerWorld.{getBlockAt, getHighestBlockAt}

      val coordinateX = random.nextInt(teleportBoundary * 2) - teleportBoundary
      val coordinateZ = random.nextInt(teleportBoundary * 2) - teleportBoundary

      // Highest safe location in nether to spawn, excluding bedrock.
      if (environment == Environment.NETHER) {
        for (coordinateY <- 124 to 2 by -1) {
          val blockAtLocation = getBlockAt(coordinateX, coordinateY, coordinateZ)
          if (blockAtLocation.isEmpty) {
            val blockOneBelow = getBlockAt(coordinateX, coordinateY - 1, coordinateZ)
            if (blockOneBelow.isEmpty) {
              val blockTwoBelow = getBlockAt(coordinateX, coordinateY - 2, coordinateZ)
              if (!blockTwoBelow.isEmpty && !blockTwoBelow.isLiquid)
                return blockTwoBelow.getLocation
            }
          }
        }
        getTeleportLocation
      } else {
        val highestBlock = getHighestBlockAt(coordinateX, coordinateZ)
        if (highestBlock.isLiquid)
          return getTeleportLocation

        highestBlock.getLocation
      }
    }

    val teleportLocaton = getTeleportLocation
    val teleportResult = player.teleport(teleportLocaton)

    if (!teleportResult) {
      player.sendMessage(s"${ChatColor.RED}[WildernessTeleport]: Teleportation failed. Please retry.")
    } else {
      cooldownMap.put(player.getUniqueId, System.currentTimeMillis())
      invulnerabilityMillis match {
        case Some(value) =>
          val invulnerabilitySpan =
            if (environment == Environment.NETHER) {
              value * 2
            } else {
              value
            }

          val friendlyInvulnerabilitySpan = millisecondsToFriendlyString(invulnerabilitySpan)
          player.sendMessage(s"${ChatColor.GOLD}[WildernessTeleport]: Succesfully teleported. You're invincible for $friendlyInvulnerabilitySpan.")

        case _ => player.sendMessage(s"${ChatColor.GOLD}[WildernessTeleport]: Successfully teleported. Good luck.")
      }
    }

    teleportResult
  }
}
