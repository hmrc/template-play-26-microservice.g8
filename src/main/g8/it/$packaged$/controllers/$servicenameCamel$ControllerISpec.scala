package $package$.controllers

import org.scalatest.Suite
import org.scalatestplus.play.ServerProvider
import play.api.libs.json.Json
import play.api.libs.ws.{WSClient, WSResponse}
import $package$.stubs.AuthStubs
import $package$.support.ServerBaseISpec

class $servicenameCamel$ControllerISpec extends ServerBaseISpec with AuthStubs {

  this: Suite with ServerProvider =>

  val url = s"http://localhost:\$port/$servicenameHyphen$"

  val wsClient = app.injector.instanceOf[WSClient]

  def entity(): WSResponse =
    wsClient
      .url(s"\$url/entities")
      .get()
      .futureValue

  "$servicenameCamel$Controller" when {

    "GET /entities" should {
      "respond with some data" in {
        givenAuthorisedAsValidAgent("ARN0001")
        val result = entity()
        result.status shouldBe 200
        result.json shouldBe Json.obj("parameter1" -> "hello ARN0001")
      }
    }
  }
}
