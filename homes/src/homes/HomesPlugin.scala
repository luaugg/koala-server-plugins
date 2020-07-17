package homes

import java.util.UUID
import java.util.concurrent.Executors

import cats.effect._
import doobie._
import doobie.hikari._
import org.bukkit.plugin.java.JavaPlugin

import scala.collection.mutable
import scala.concurrent.ExecutionContext

class HomesPlugin extends JavaPlugin {
  var cooldownMap: mutable.Map[UUID, Long] = mutable.Map()
  var cooldownMillis: Option[Long] = _
  var invulnerabilityMillis: Option[Long] = _

  var maximumHomeCount: Option[Int] = _
  var allowDeletingHomes: Boolean = _
  var allowOverridingHomes: Boolean = _

  var allowOverworldHomes: Boolean = _
  var allowNetherHomes: Boolean = _
  var allowEndHomes: Boolean = _

  var allowNamingHomes: Boolean = _
  var homeNameCharLimit: Option[Int] = _

  override def onEnable(): Unit = {
    // Setup database stuff.
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
