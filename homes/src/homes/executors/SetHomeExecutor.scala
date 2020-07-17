package homes.executors

import cats.effect.IO
import doobie.hikari.HikariTransactor
import homes.HomesPlugin

case class SetHomeExecutor(plugin: HomesPlugin, transactor: HikariTransactor[IO]) {

}
