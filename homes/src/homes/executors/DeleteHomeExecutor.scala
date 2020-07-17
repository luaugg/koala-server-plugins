package homes.executors

import cats.effect.IO
import doobie.hikari.HikariTransactor
import homes.HomesPlugin

case class DeleteHomeExecutor(plugin: HomesPlugin, transactor: HikariTransactor[IO]) {

}
