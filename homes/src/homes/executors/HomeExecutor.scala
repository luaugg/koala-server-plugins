package homes.executors

import cats.effect.IO
import doobie.hikari.HikariTransactor
import homes.HomesPlugin

case class HomeExecutor(plugin: HomesPlugin, transactor: HikariTransactor[IO]) {

}
