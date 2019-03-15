package io.antonkw

import org.scalatest.FreeSpec
import scalaz.Scalaz._
import scalaz.{\/, _}

/**
  * Based on:
  * https://tpolecat.github.io/2014/03/21/functor.html
  */
class FunctorDemo extends FreeSpec {
  case class Box2[A](fst: A, snd: A)
  implicit val boxFunctor = new Functor[Box2] {
    def map[A, B](fa: Box2[A])(f: A => B): Box2[B] = Box2(f(fa.fst), f(fa.snd))
  }

  implicit val equal = new Equal[Box2[Int]] {
    override def equal(a1: Box2[Int], a2: Box2[Int]): Boolean = (a1.fst ≟ a2.fst) && (a1.snd ≟ a2.snd)
  }

  implicit val show = Show.showA[Box2[Int]]

  "Functor instance" in {
    val F = Functor[Box2]

    F.map(Box2("123", "x"))(_.length) assert_=== Box2(3, 1)
    F.lift((s: String) => s.length)(Box2("123", "x")) assert_=== Box2(3, 1)
  }

  "map and lift" in {
    Box2("123", "x").map(_.length) assert_=== Box2(3, 1)
    ((s: String) => s.length).lift(Functor[Box2])(Box2("123", "x")) assert_=== Box2(3, 1)
  }

  "mapply" in {
    def add1(n:Int) = n + 1
    def times2(n:Int) = n * 2
    def mapToInt: Box2[Int => Int] = Box2(int => times2(int), int => add1(int))

    Functor[Box2].mapply(10)(mapToInt) assert_=== Box2(20, 11)
  }

  "pairing" in {
    assert(
      Box2(1, 2).fpair == Box2((1,1), (2, 2)),
      "fpair produce tuples, every tuple is duplication of orginal value"
    )

    assert(
      Box2("abc", "x").strengthL(1) == Box2((1, "abc"),(1, "x")),
      "strengthL add constant as first element of tuple"
    )

    assert(
      Box2("abc", "x").strengthR(1) == Box2(("abc", 1),("x", 1)),
      "strengthR add constant as second element of tuple"
    )

    assert(
      Box2("foo", "x").fproduct(_.length) == Box2(("foo", 3),("x", 1)),
      "fproduct add result of function applying as second element of tuple"
    )
  }

  "miscellaneous" in {
    assert(
      Box2("foo", "x").void == Box2((),()),
      "void retains only structure"
    )

    assert(
      Box2("foo", "x").fpoint[List] == Box2(List("foo"), List("x")),
      "fpoint lifts the parameterized type into a given Applicative"
    )

    assert(
      Functor[Box2].counzip(Box2(1, 2).right[Box2[String]]) == Box2(\/.right[String, Int](1), \/.right[String, Int](2))
    )

    assert(
      Functor[Box2].counzip(Box2(1, 2).left[Box2[String]]) == Box2(\/.left[Int, String](1), \/.left[Int, String](2))
    )
  }

  "compose" in {
    val f = Functor[Box2] compose Functor[Option]

    assert(
      f.map(Box2[Option[Int]](Some(1), None))(_ + 1) == Box2(Some(2), None)
    )
  }

  "product" in {
    val f = Functor[Box2] product Functor[Option]

    assert(
      f.map((Box2(1, 5), Some(3)))(_ + 1) == ((Box2(2, 6), Some(4)))
    )
  }

  "bifunctor" in {
    val f = Functor[Box2] bicompose Bifunctor[\/]
    assert(
      f.bimap(Box2[\/[Int, String]](-\/(10), \/-("w")))(_ + 1, _.toUpperCase) == Box2(-\/(11),\/-("W"))
    )
  }
}
