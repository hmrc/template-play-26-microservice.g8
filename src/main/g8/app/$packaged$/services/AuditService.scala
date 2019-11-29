package $package$.services

import javax.inject.Inject

import com.google.inject.Singleton
import play.api.mvc.Request
import uk.gov.hmrc.agentmtdidentifiers.model.Arn
import $package$.models.$servicenameCamel$Model
import uk.gov.hmrc.http.HeaderCarrier
import uk.gov.hmrc.play.audit.AuditExtensions._
import uk.gov.hmrc.play.audit.http.connector.AuditConnector
import uk.gov.hmrc.play.audit.model.DataEvent
import scala.concurrent.ExecutionContext

import scala.concurrent.Future
import scala.util.Try

object $servicenameCamel$WithMongodbEvent extends Enumeration {
  val $servicenameCamel$WithMongodbSomethingHappened = Value
  type $servicenameCamel$WithMongodbEvent = Value
}

@Singleton
class AuditService @Inject()(val auditConnector: AuditConnector) {

  import $servicenameCamel$WithMongodbEvent._

  def send$servicenameCamel$WithMongodbSomethingHappened(
    model: $servicenameCamel$Model,
    agentReference: Arn)(implicit hc: HeaderCarrier, request: Request[Any], ec: ExecutionContext): Unit =
    auditEvent(
      $servicenameCamel$WithMongodbEvent.$servicenameCamel$WithMongodbSomethingHappened,
      "$servicenameHyphen$-with-mongodb-something-happened",
      Seq(
        "agentReference"  -> agentReference.value,
        "parameter1"      -> model.parameter1,
        "telephoneNumber" -> model.telephoneNumber.getOrElse(""),
        "emailAddress"    -> model.emailAddress.getOrElse("")
      )
    )

  private[services] def auditEvent(
    event: $servicenameCamel$WithMongodbEvent,
    transactionName: String,
    details: Seq[(String, Any)] = Seq.empty)(
    implicit hc: HeaderCarrier,
    request: Request[Any],
    ec: ExecutionContext): Future[Unit] =
    send(createEvent(event, transactionName, details: _*))

  private[services] def createEvent(
    event: $servicenameCamel$WithMongodbEvent,
    transactionName: String,
    details: (String, Any)*)(implicit hc: HeaderCarrier, request: Request[Any], ec: ExecutionContext): DataEvent = {

    val detail = hc.toAuditDetails(details.map(pair => pair._1 -> pair._2.toString): _*)
    val tags = hc.toAuditTags(transactionName, request.path)
    DataEvent(auditSource = "$servicenameHyphen$-with-mongodb", auditType = event.toString, tags = tags, detail = detail)
  }

  private[services] def send(events: DataEvent*)(implicit hc: HeaderCarrier, ec: ExecutionContext): Future[Unit] =
    Future {
      events.foreach { event =>
        Try(auditConnector.sendEvent(event))
      }
    }

}
