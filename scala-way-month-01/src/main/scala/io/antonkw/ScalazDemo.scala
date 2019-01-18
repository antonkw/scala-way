package io.antonkw

import com.typesafe.scalalogging.LazyLogging
import scalaz._
import Scalaz._

object ScalazDemo extends App with LazyLogging {

  val option1: Option[String] = "string1".some
  val option2: Option[String] = "string2".some

  val isOptionEquals = option1 ≟ option2


  val strList1 = List("a", "b", "c")
  val strList2 = List("a", "b", "c")

  val compare = strList1 ≟ strList2

  logger.info(compare.toString)

}
