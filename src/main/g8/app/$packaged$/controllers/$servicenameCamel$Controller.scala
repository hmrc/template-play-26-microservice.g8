/*
 * Copyright 2020 HM Revenue & Customs
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

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
