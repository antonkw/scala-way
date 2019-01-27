package io.antonkw

import org.scalatest.FreeSpec
import scalaz._


class OrderDemo extends FreeSpec {
  "Basic scala operators works good with different types" in {
    assert(1 < 2.0)
    assert(3 > 2.0)
  }

  "using gt,lte... operators allows to do type-safe comparisons" in {
    import Scalaz._

    assert(1 lt 2)
    assertDoesNotCompile("1 lte 2.0")
  }

  "for Option[T] all comparison operators works as well, non is less that any other value" in {
    val (one, two): (Option[Int], Option[Int] ) = (Some(1), Some(2))
    assertDoesNotCompile("one < two")

    import Scalaz._
    assert(one < two)

    assert(none[Int] < Int.MinValue.some)
    assert(none[Int] eq none[Int])
  }

  "by wrapping value by Some(value) type of value will be Some[T] and implicits for Option[T] are now working" in {
    import Scalaz._
    val (one, two) = (Some(1), Some(2)) //types are Some[Int]
    assertDoesNotCompile("one < two")
  }

  ".some syntax return provide Option[T]" in {
    import Scalaz._

    val (one, two) = (1.some, 2.some)
    assert(one < two)
  }

  "?|? returns Ordering" in {
    import Scalaz._
    assert(1.0 ?|? 2.0 == Ordering.LT)
  }

}
