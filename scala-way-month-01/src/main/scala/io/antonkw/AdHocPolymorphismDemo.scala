package io.antonkw

import com.typesafe.scalalogging.LazyLogging

/*
This is truely ad-hoc in the sense that
1. we can provide separate function definitions for different types of A
2. we can provide function definitions to types (like Int) without access to
its source code
3. the function definitions can be enabled or disabled in different scopes
 */
object AdHocPolymorphismDemo extends App with LazyLogging {
  trait Plus[A] {
    def plus(a1: A, a2: A): A
  }

  implicit val plus1 = new Plus[BucketOfOranges] {
    override def plus(a1: BucketOfOranges, a2: BucketOfOranges): BucketOfOranges = BucketOfOranges(a1.count + a2.count)
  }

  val plus2 = new Plus[BucketOfOranges] {
    override def plus(a1: BucketOfOranges, a2: BucketOfOranges): BucketOfOranges = BucketOfOranges(a1.count + a2.count + 3)
  }

  case class BucketOfOranges(count: Int)

  def plus[A: Plus](a1: A, a2: A): A = {
    val plus = implicitly[Plus[A]]
    plus.plus(a1, a2)
  }

  val b1 = BucketOfOranges(2)
  val b2 = BucketOfOranges(3)

  val b3 = plus(b1, b2)(plus2)

  logger.info(b3.toString)

}
