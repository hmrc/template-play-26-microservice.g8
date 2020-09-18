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

package $package$.repository

import javax.inject.{Inject, Singleton}

import play.api.libs.json.Format
import play.api.libs.json.Json.{format, toJsFieldJsValueWrapper}
import play.modules.reactivemongo.ReactiveMongoComponent
import reactivemongo.api.commands.WriteResult
import reactivemongo.api.indexes.Index
import reactivemongo.api.indexes.IndexType.Ascending
import reactivemongo.bson.BSONObjectID
import uk.gov.hmrc.mongo.ReactiveRepository
import uk.gov.hmrc.mongo.json.ReactiveMongoFormats

import scala.collection.Seq
import scala.concurrent.{ExecutionContext, Future}

case class $servicenameCamel$WithMongodbEntity(id: String, dummy: String)

object $servicenameCamel$WithMongodbEntity extends ReactiveMongoFormats {
  implicit val formats: Format[$servicenameCamel$WithMongodbEntity] =
    format[$servicenameCamel$WithMongodbEntity]
}

@Singleton
class $servicenameCamel$WithMongodbRepository @Inject() (mongoComponent: ReactiveMongoComponent)
    extends ReactiveRepository[$servicenameCamel$WithMongodbEntity, BSONObjectID](
      "$servicenameHyphen$-with-mongodb",
      mongoComponent.mongoConnector.db,
      $servicenameCamel$WithMongodbEntity.formats,
      ReactiveMongoFormats.objectIdFormats
    ) with StrictlyEnsureIndexes[$servicenameCamel$WithMongodbEntity, BSONObjectID] {

  def findBy(
    id: String
  )(implicit ec: ExecutionContext): Future[List[$servicenameCamel$WithMongodbEntity]] =
    find(
      Seq("id" -> Some(id)).map(option => option._1 -> toJsFieldJsValueWrapper(option._2.get)): _*
    )

  override def indexes =
    Seq(
      Index(Seq("id" -> Ascending), Some("$servicenameCamel$WithMongodb"), unique = true)
    )

  def createEntity(id: String, dummy: String)(implicit ec: ExecutionContext): Future[Unit] =
    insert($servicenameCamel$WithMongodbEntity(id, dummy)).map(_ => ())

  def delete(id: String)(implicit ec: ExecutionContext): Future[WriteResult] =
    remove("id" -> id)

}
