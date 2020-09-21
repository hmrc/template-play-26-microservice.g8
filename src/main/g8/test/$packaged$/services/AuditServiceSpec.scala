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

package $package$.services

import org.mockito.ArgumentCaptor
import org.mockito.ArgumentMatchers._
import org.mockito.Mockito._
import org.scalatest.concurrent.Eventually
import org.scalatestplus.mockito.MockitoSugar
import org.scalatest.time.{Millis, Span}
import play.api.test.FakeRequest
import uk.gov.hmrc.agentmtdidentifiers.model.Arn
import $package$.models.$servicenameCamel$Model
import uk.gov.hmrc.http.HeaderCarrier
import uk.gov.hmrc.http.logging.{Authorization, RequestId, SessionId}
import uk.gov.hmrc.play.audit.http.connector.AuditConnector
import uk.gov.hmrc.play.audit.model.DataEvent
import uk.gov.hmrc.play.test.UnitSpec

import scala.concurrent.ExecutionContext

class AuditServiceSpec extends UnitSpec with MockitoSugar with Eventually {

  override implicit val patienceConfig =
    PatienceConfig(timeout = scaled(Span(500, Millis)), interval = scaled(Span(200, Millis)))

  "auditService" should {

    "send an $servicenameCamel$SomethingHappened event with the correct fields" in {
      val mockConnector = mock[AuditConnector]
      val service = new AuditService(mockConnector)

      val hc = HeaderCarrier(
        authorization = Some(Authorization("dummy bearer token")),
        sessionId = Some(SessionId("dummy session id")),
        requestId = Some(RequestId("dummy request id"))
      )

      val model = $servicenameCamel$Model(
        parameter1 = "John Smith",
        parameter2 = None,
        telephoneNumber = Some("12313"),
        emailAddress = Some("john.smith@email.com")
      )

      await(
        service.send$servicenameCamel$SomethingHappened(model, Arn("ARN0001"))(
          hc,
          FakeRequest("GET", "/path"),
          ExecutionContext.Implicits.global
        )
      )

      eventually {
        val captor = ArgumentCaptor.forClass(classOf[DataEvent])
        verify(mockConnector).sendEvent(captor.capture())(any[HeaderCarrier], any[ExecutionContext])
        val sentEvent = captor.getValue.asInstanceOf[DataEvent]

        sentEvent.auditType shouldBe "$servicenameCamel$SomethingHappened"
        sentEvent.auditSource shouldBe "$servicenameHyphen$"
        sentEvent.detail("agentReference") shouldBe "ARN0001"
        sentEvent.detail("parameter1") shouldBe "John Smith"
        sentEvent.detail("telephoneNumber") shouldBe "12313"
        sentEvent.detail("emailAddress") shouldBe "john.smith@email.com"

        sentEvent.tags(
          "transactionName"
        ) shouldBe "$servicenameHyphen$-something-happened"
        sentEvent.tags("path") shouldBe "/path"
        sentEvent.tags("X-Session-ID") shouldBe "dummy session id"
        sentEvent.tags("X-Request-ID") shouldBe "dummy request id"
      }
    }
  }
}
