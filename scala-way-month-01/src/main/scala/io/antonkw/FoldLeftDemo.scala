package io.antonkw

import com.typesafe.scalalogging.LazyLogging

object FoldLeftDemo extends App with LazyLogging {
  trait Monoid[A] {
    def mappend(a1: A, a2: A): A
    def mzero: A
  }
  object Monoid {
    implicit val IntMonoid: Monoid[Int] = new Monoid[Int] {
      def mappend(a: Int, b: Int): Int = a + b
      def mzero: Int = 0
    }
    implicit val StringMonoid: Monoid[String] = new Monoid[String] {
      def mappend(a: String, b: String): String = a + b
      def mzero: String = ""
    }
  }

  def sum[A: Monoid](xs: List[A]): A = {
    val m = implicitly[Monoid[A]]
    xs.foldLeft(m.mzero)(m.mappend)
  }

  def sum[M[_] : FoldLeft, A: Monoid](xs: M[A]): A = {
    val m = implicitly[Monoid[A]]
    val fl = implicitly[FoldLeft[M]]
    fl.foldLeft(xs, m.mzero, m.mappend)
  }

  trait FoldLeft[F[_]] {
    def foldLeft[A, B](xs: F[A], b: B, f: (B, A) => B): B
  }

  object FoldLeft {
    implicit val FoldLeftList: FoldLeft[List] = new FoldLeft[List] {
      def foldLeft[A, B](xs: List[A], b: B, f: (B, A) => B): B = xs.foldLeft(b)(f)
    }
  }

  trait MonoidOp[A] {
    val F: Monoid[A]
    val value: A
    def |+|(a2: A) = F.mappend(value, a2)
  }

  implicit def toMonoidOp[A: Monoid](a: A): MonoidOp[A] = new MonoidOp[A] {
    val F = implicitly[Monoid[A]]
    val value = a
  }

  // scalastyle:off magic.number
  val intList = List(1, 2, 3, 4)
  // scalastyle:on magic.number

  val stringList = List("a", "b", "c")

  val intSum = sum(intList)
  val stringSum = sum(stringList)

  logger.info(s"Sum of $intList: $intSum, sum of $stringList: $stringSum")

  logger.info("In the above example, the traits Monoid and FoldLeft correspond to Haskell’s typeclass")

  logger.info("l" |+| "x")
}