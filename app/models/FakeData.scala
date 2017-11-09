package models

import java.util.{Calendar, Date}

import play.api.libs.json.Json

case class FakeData(data:String)
case class TestData(testId:String,requestSequenceNumber:Int,timestampInMillis:Long)
case class TestResult(testId:String,timestamp:Date,accumulatedTime:Long,numberOfRequests:Int,success:Int,failures:Int)

object FakeData {

  implicit val fakeDataWriter = Json.writes[FakeData]
  implicit val fakeDataReader = Json.reads[FakeData]

  implicit val testDataWriter = Json.writes[TestData]
  implicit val testDataReader = Json.reads[TestData]

  implicit val testResultWriter = Json.writes[TestResult]
  implicit val testResultReader = Json.reads[TestResult]


  def produceData(size:Int) = {
    val array = Array.fill[Long](size)(Math.random().round % 2)
    FakeData(array.mkString(""))
  }

}
