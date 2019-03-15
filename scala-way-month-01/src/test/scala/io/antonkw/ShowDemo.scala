package io.antonkw

import org.scalatest.FreeSpec
import scalaz.Scalaz._
import scalaz._

class ShowDemo extends FreeSpec {
  /*
   Under the hood:
   def show(f: F): Cord = Cord(shows(f))
   def shows(f: F): String = show(f).toString
   */
  "xs" in {
    case class MyClass(n: Int)

    implicit val show = new Show[MyClass] {
      override def shows(f: MyClass): String = s"MyClass with ${f.n}"
    }

    val instance = MyClass(5)

    instance.show assert_=== Cord("MyClass with 5")
    instance.println
  }
}
