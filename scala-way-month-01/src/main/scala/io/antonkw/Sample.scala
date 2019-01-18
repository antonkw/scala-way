package io.antonkw

import com.typesafe.scalalogging.StrictLogging

object Sample extends App with StrictLogging{
  val name = "Without quotes"

  if (true) {
    val `name` = "With quotes"

    logger.info(name)
  }
}
