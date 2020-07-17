package homes.executors

import cats.effect.IO
import doobie.hikari.HikariTransactor
import homes.HomesPlugin
import org.bukkit.command.{Command, CommandExecutor, CommandSender}

case class DeleteHomeExecutor(plugin: HomesPlugin, transactor: HikariTransactor[IO]) extends CommandExecutor {
  override def onCommand(sender: CommandSender, command: Command, label: String, args: Array[String]): Boolean = {
    true
  }
}