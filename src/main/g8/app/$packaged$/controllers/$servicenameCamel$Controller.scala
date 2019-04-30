package $package$.controllers

import javax.inject.{Inject, Singleton}
import play.api.libs.json.Json.toJson
import play.api.mvc._
import play.api.{Configuration, Environment}
import uk.gov.hmrc.agentmtdidentifiers.model.Utr
import $package$.connectors.MicroserviceAuthConnector
import $package$.models.$servicenameCamel$Model
import uk.gov.hmrc.play.bootstrap.controller.BackendController

import scala.concurrent.{ExecutionContext, Future}

@Singleton
class $servicenameCamel$Controller @Inject()(
  val authConnector: MicroserviceAuthConnector,
  val env: Environment,
  cc: ControllerComponents)(implicit val configuration: Configuration, ec: ExecutionContext)
    extends BackendController(cc) with AuthActions {

  def entities: Action[AnyContent] = Action.async { implicit request =>
    Future.successful(Ok(toJson($servicenameCamel$Model("hello world", None, None, None))))
  }

  def entitiesByUtr(utr: Utr): Action[AnyContent] = Action.async { implicit request =>
    Future.successful(Ok(toJson($servicenameCamel$Model(s"hello \$utr", None, None, None))))
  }

}
