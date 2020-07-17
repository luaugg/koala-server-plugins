import mill._, scalalib._
import coursier.maven.MavenRepository

trait CommonModule extends ScalaModule {
  def scalaVersion = "2.13.1"

  override def ivyDeps = Agg(
    ivy"com.destroystokyo.paper:paper-api:1.16.1-R0.1-SNAPSHOT"
  )

  override def repositories = super.repositories ++ Seq(
    MavenRepository("https://papermc.io/repo/repository/maven-public/")
  )
}

object utils extends CommonModule

object wildtp extends CommonModule {
  override def moduleDeps = Seq(utils)
}

object homes extends CommonModule {
  override def moduleDeps = Seq(utils)

  // Consider changing project structure to inherit Paper API more conveniently.
  override def ivyDeps = Agg(
    ivy"com.destroystokyo.paper:paper-api:1.16.1-R0.1-SNAPSHOT",
    ivy"org.tpolecat::doobie-h2:0.9.0",
    ivy"org.tpolecat::doobie-hikari:0.9.0"
  )
}