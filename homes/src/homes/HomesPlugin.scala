package homes

import java.util.concurrent.Executors

import cats.effect._
import doobie._
import doobie.hikari._
import org.bukkit.plugin.java.JavaPlugin

import scala.concurrent.ExecutionContext

class HomesPlugin extends JavaPlugin {
  override def onEnable(): Unit = {
    val availableProcessors = math.max(2, sys.runtime.availableProcessors)
    val backingThreadPool = ExecutionContext.fromExecutor(Executors.newFixedThreadPool(availableProcessors))
    implicit val contextShift: ContextShift[IO] = IO.contextShift(backingThreadPool)

    val databaseTransactorResource =
      for {
        connectionContext <- ExecutionContexts.fixedThreadPool[IO](availableProcessors * 2)
        blockingContext <- Blocker[IO]
        hikariTransactor <- HikariTransactor.newHikariTransactor[IO](
          "org.h2.Driver",
          "jdbc:h2:./db",
          "sa",
          "",
          connectionContext,
          blockingContext
        )
      } yield hikariTransactor
  }
}
