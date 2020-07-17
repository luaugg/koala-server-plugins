package homes

import java.util.UUID
import java.util.concurrent.Executors

import cats.effect._
import doobie._
import doobie.hikari._
import org.bukkit.configuration.file.FileConfiguration
import org.bukkit.plugin.java.JavaPlugin

import scala.collection.mutable
import scala.concurrent.ExecutionContext

class HomesPlugin extends JavaPlugin {
  import utils.config._

  var cooldownMap: mutable.Map[UUID, Long] = mutable.Map()

  def cooldownMillis =        "cooldownMillis".resolveOptionalInt
  def invulnerabilityMillis = "invulnerabilityMillis".resolveOptionalInt

  def maximumHomeCount =      "homes.maximumNumberOfHomes".resolveOptionalInt
  def allowDeletingHomes =    "homes.allowDeletingHomes".resolveBoolean
  def allowOverridingHomes =  "homes.allowOverridingHomes".resolveBoolean

  def allowOverworldHomes =   "homes.allowedLocations.overworldHomes".resolveBoolean
  def allowNetherHomes =      "homes.allowedLocations.netherHomes".resolveBoolean
  def allowEndHomes =         "homes.allowedLocations.endHomes".resolveBoolean

  def allowNamingHomes =      "homes.naming.allowNamingHomes".resolveBoolean
  def homeNameCharLimit =     "homes.naming.homeNameCharLimit".resolveOptionalInt

  override def onEnable(): Unit = {
    saveDefaultConfig()

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

  private implicit def config: FileConfiguration = getConfig
}
