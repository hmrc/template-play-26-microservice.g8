package $package$.support

import org.scalatestplus.play.OneAppPerSuite
import play.api.Application
import $package$.stubs.AuthStubs

abstract class AppBaseISpec
    extends BaseISpec with OneAppPerSuite with TestApplication with AuthStubs {

  override implicit lazy val app: Application = appBuilder.build()

}
