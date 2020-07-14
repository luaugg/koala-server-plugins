import mill._, scalalib._
import coursier.maven.MavenRepository

object wildtp extends ScalaModule {
  def scalaVersion = "2.13.1"

  override def ivyDeps = Agg(
    ivy"com.destroystokyo.paper:paper-api:1.16.1-R0.1-SNAPSHOT"
  )

  override def repositories = super.repositories ++ Seq(
    MavenRepository("https://papermc.io/repo/repository/maven-public/")
  )
}
