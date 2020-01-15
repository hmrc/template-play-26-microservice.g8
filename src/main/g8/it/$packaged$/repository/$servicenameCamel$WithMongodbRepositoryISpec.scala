/*
 * Copyright 2017 HM Revenue & Customs
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

import play.api.Application
import play.api.inject.guice.GuiceApplicationBuilder
import reactivemongo.api.commands.WriteResult
import reactivemongo.core.errors.DatabaseException
import $package$.support.MongoApp
import uk.gov.hmrc.play.test.UnitSpec

import scala.concurrent.ExecutionContext.Implicits.global

class $servicenameCamel$WithMongodbRepositoryISpec extends UnitSpec with MongoApp {

  protected def appBuilder: GuiceApplicationBuilder =
    new GuiceApplicationBuilder()
      .configure(
        "mongodb.uri" -> s"mongodb://127.0.0.1:27017/test-\${this.getClass.getSimpleName}"
      )

  override implicit lazy val app: Application = appBuilder.build()

  def repo: $servicenameCamel$WithMongodbRepository =
    app.injector.instanceOf[$servicenameCamel$WithMongodbRepository]

  override def beforeEach() {
    super.beforeEach()
    await(repo.ensureIndexes)
  }

  "createEntity" should {
    "create an entity" in {
      await(repo.createEntity("foo", "bar"))

      val result = await(repo.find())

      result.size shouldBe 1
      result.head.id shouldBe "foo"
      result.head.dummy shouldBe "bar"

    }

    "not allow duplicate entities to be created for the same id" in {
      await(repo.createEntity("foo", "bar"))

      val e = intercept[DatabaseException] {
        await(repo.createEntity("foo", "bar"))
      }

      e.getMessage() should include("E11000")
    }
  }
}
