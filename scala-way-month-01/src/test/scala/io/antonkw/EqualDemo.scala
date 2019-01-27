package io.antonkw

import org.scalatest.FreeSpec
import scalaz._
import Scalaz._


class EqualDemo extends FreeSpec {
  "Equal produce compile time error for different types, default scala '==' just returns false" in {
    assertCompiles("1 == 1.0")
    assertResult(false)(1 == "1")

    assertDoesNotCompile("1 =/= 1.0")
  }

  "assert_=== returns Unit and throws exception" in
    assertThrows[RuntimeException](1 assert_=== 2, "assert_=== throw RuntimeException in case of mismatch")

  "default == can compare various types" in assertCompiles("1 == 1.0")

  "=== is not defined for any object" in assertDoesNotCompile("Seq(1) === Seq(1)")

  "to use Equal for custom classes you need to define Equal[T]" in {
    case class StringDecorator(s: String)

    val a = StringDecorator("string")
    val b = StringDecorator("string")

    assertDoesNotCompile("a ≟ b")

    implicit val StringDecoratorEqual: Equal[StringDecorator] = Equal.equalA[StringDecorator]

    assertCompiles("a ≟ b")

    assert(a ≟ b)
    assert(StringDecorator("string") ≠ StringDecorator("another string"))
  }

}
