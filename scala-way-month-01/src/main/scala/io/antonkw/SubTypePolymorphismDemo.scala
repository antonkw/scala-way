package io.antonkw

import com.typesafe.scalalogging.LazyLogging

object SubTypePolymorphismDemo extends App with LazyLogging {

  trait Plus[A] {
    def plus(a2: A): A
  }

  class Plusable(val number: Int) extends Plus[Plusable] {
    override def plus(a2: Plusable): Plusable = new Plusable(number + a2.number)
  }

  def plus[A <: Plus[A]](a1: A, a2: A): A = a1.plus(a2)

  val a = new Plusable(1)
  val b = new Plusable(2)

  val plusResult = plus(a, b)

  logger.info(s"Plusable: $plusResult")

//  case class PlusableWithName(name: String) extends Plusable(number = 0) with Plus[PlusableWithName] {
//    override def plus(a2: PlusableWithName): PlusableWithName = PlusableWithName("") //NON-COMPILE
//  }
//  val po = PlusableWithName(name = "Something")
//
//  plus(po, po)
}
