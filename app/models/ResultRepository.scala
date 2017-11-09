package models

import scala.collection.mutable

object ResultRepository {
  val data = new mutable.MutableList[TestResult]()

  def put(result:TestResult) =  data+= result

  def clear = data.clear()

}
