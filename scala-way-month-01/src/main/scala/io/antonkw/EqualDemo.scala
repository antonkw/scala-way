package io.antonkw

import com.typesafe.scalalogging.LazyLogging
import scalaz._
import Scalaz._

object EqualDemo extends App with LazyLogging {
  logger.info(s"1 === 1: ${1 === 1}")
  logger.info(s"1 === 'str': ${1 == "str"}")
  //1 === "str" not compile
  //1 assert_=== "1" need implicits
  1 assert_=== 1 //throw runtime exception if not


}
