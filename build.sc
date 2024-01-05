// import Mill dependency
import mill._
import mill.define.Sources
import mill.modules.Util
import mill.scalalib.TestModule.ScalaTest
import scalalib._
// support BSP
import mill.bsp._

object cde_test extends SbtModule { m =>
  override def moduleDeps = Seq(cde)
	override def millSourcePath = os.pwd
  override def scalaVersion = "2.13.10"
  override def scalacOptions = Seq(
    "-language:reflectiveCalls",
    "-deprecation",
    "-feature",
    "-Xcheckinit",
  )
  override def ivyDeps = Agg(
    ivy"edu.berkeley.cs::chisel3:3.5.6",
  )
  override def scalacPluginIvyDeps = Agg(
    ivy"edu.berkeley.cs:::chisel3-plugin:3.5.6",
  )
  object test extends SbtModuleTests with TestModule.ScalaTest {
    override def ivyDeps = m.ivyDeps() ++ Agg(
      ivy"edu.berkeley.cs::chiseltest:0.5.6"
    )
  }
}

object cde extends SbtModule {
  override def millSourcePath = os.pwd / "cde"
	override def sources = T.sources {
		super.sources() ++ Seq(PathRef(millSourcePath / "cde" / "src" / "chipsalliance" / "rocketchip"))
	}
  override def scalaVersion = "2.13.10"
  override def ivyDeps = Agg(
    ivy"com.lihaoyi::utest:0.8.1",
  )
}
