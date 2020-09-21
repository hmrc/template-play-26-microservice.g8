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

import play.api.mvc.{Request, Result}
import uk.gov.hmrc.agentmtdidentifiers.model.{Arn, MtdItId}
import uk.gov.hmrc.auth.core.AuthProvider.GovernmentGateway
import uk.gov.hmrc.auth.core._
import uk.gov.hmrc.auth.core.retrieve.v2.Retrievals.authorisedEnrolments
import uk.gov.hmrc.http.HeaderCarrier

import scala.concurrent.{ExecutionContext, Future}

trait AuthActions extends AuthorisedFunctions {

  protected def withAuthorisedAsAgent[A](
    body: Arn => Future[Result]
  )(implicit request: Request[A], hc: HeaderCarrier, ec: ExecutionContext): Future[Result] =
    withEnrolledFor("HMRC-AS-AGENT", "AgentReferenceNumber") {
      case Some(arn) => body(Arn(arn))
      case None =>
        Future.failed(InsufficientEnrolments("AgentReferenceNumber identifier not found"))
    }

  protected def withAuthorisedAsClient[A](
    body: MtdItId => Future[Result]
  )(implicit request: Request[A], hc: HeaderCarrier, ec: ExecutionContext): Future[Result] =
    withEnrolledFor("HMRC-MTD-IT", "MTDITID") {
      case Some(mtdItID) => body(MtdItId(mtdItID))
      case None          => Future.failed(InsufficientEnrolments("MTDITID identifier not found"))
    }

  protected def withEnrolledFor[A](serviceName: String, identifierKey: String)(
    body: Option[String] => Future[Result]
  )(implicit request: Request[A], hc: HeaderCarrier, ec: ExecutionContext): Future[Result] =
    authorised(
      Enrolment(serviceName)
        and AuthProviders(GovernmentGateway)
    )
      .retrieve(authorisedEnrolments) { enrolments =>
        val id = for {
          enrolment  <- enrolments.getEnrolment(serviceName)
          identifier <- enrolment.getIdentifier(identifierKey)
        } yield identifier.value

        body(id)
      }

}
