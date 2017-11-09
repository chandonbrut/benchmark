package controllers

import java.util.UUID

import com.google.inject.Inject
import models.{FakeData, ResultRepository, TestData, TestResult}
import play.api.libs.json.{JsError, Json}
import play.api.mvc.{Action, BaseController, ControllerComponents}


class BenchmarkController @Inject() (val controllerComponents: ControllerComponents) extends BaseController {

  implicit val fakeDataWriter = Json.writes[FakeData]
  implicit val fakeDataReader = Json.reads[FakeData]

  implicit val testDataWriter = Json.writes[TestData]
  implicit val testDataReader = Json.reads[TestData]

  implicit val testResultWriter = Json.writes[TestResult]
  implicit val testResultReader = Json.reads[TestResult]

  private def randomId():String = {
    UUID.randomUUID().toString
  }

  def generateTestId() = Action {
    Ok(Json.obj("testId" -> "newId"))
  }

  def markAndReturn() = Action {
    Ok(Json.toJson(FakeData.produceData(1024)))
  }

  def viewResults(testId:String) = Action {
    val result = ResultRepository.data.filter(p => p.testId == testId).head
    Ok(views.html.results(result))
  }

  def viewTests() = Action {
    Ok(views.html.list(ResultRepository.data.toList))
  }

  def postResults = Action(parse.json) {
    request => {
      val testResult = request.body.validate[TestResult]
      testResult.fold(
        errors => BadRequest(Json.obj("error" -> JsError.toJson(errors))),
        testResult => {
          ResultRepository.put(testResult)
          Ok(Json.obj("status" -> "ok"))
        }
      )
    }
  }


  def loadFromJson = Action(parse.json) {
    request => {
      val testResults = request.body.validate[Array[TestResult]]
      testResults.fold(
        errors => BadRequest(Json.obj("error" -> JsError.toJson(errors))),
        testResults => {
          Ok(Json.obj("status" -> "ok"))
        }
      )
    }
  }

  def start() = Action {
    Ok(views.html.start(randomId()))
  }

  def findAllResults = Action {
    Ok(Json.toJson(ResultRepository.data))
  }


}
