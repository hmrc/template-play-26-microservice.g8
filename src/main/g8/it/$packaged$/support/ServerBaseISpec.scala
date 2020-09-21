package $package$.support

import org.scalatest.concurrent.ScalaFutures
import org.scalatest.time.{Seconds, Span}
import org.scalatestplus.play.OneServerPerSuite
import play.api.Application

abstract class ServerBaseISpec
    extends BaseISpec with OneServerPerSuite with TestApplication with ScalaFutures {

  override implicit lazy val app: Application = appBuilder.build()

  implicit override val patienceConfig: PatienceConfig =
    PatienceConfig(timeout = Span(4, Seconds), interval = Span(1, Seconds))

}
