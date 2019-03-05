package io.antonkw

import org.scalatest.FreeSpec
import scalaz.Scalaz._
import scalaz._

class EnumDemo extends FreeSpec {
  object MonthsOfYear extends Enumeration {
    type Month = Value

    private[antonkw] def firstId = 1
    private[antonkw] def lastId = maxId - 1

    //ids is defined explicitly to focus reader on the fact, that ordering is really matters
    val JANUARY: Month = Value(firstId)
    val FEBRUARY: Month = Value(nextId)
    val MARCH: Month = Value(nextId)
    val APRIL: Month = Value(nextId)
    val MAY: Month = Value(nextId)
    val JUNE: Month = Value(nextId)
    val JULY: Month = Value(nextId)
    val AUGUST: Month = Value(nextId)
    val SEPTEMBER: Month = Value(nextId)
    val OCTOBER: Month = Value(nextId)
    val NOVEMBER: Month = Value(nextId)
    val DECEMBER: Month = Value(nextId)
  }

  import MonthsOfYear._

  implicit val coloringInstance: Enum[Month] = new Enum[Month] {
    private val idToVal: Map[Int, Month] = MonthsOfYear.values.map(v => (v.id, v)).toMap
    override def succ(a: Month): Month = if (MonthsOfYear.lastId =/= a.id) idToVal(a.id + 1) else idToVal(MonthsOfYear.firstId)

    override def pred(a: Month): Month = if (MonthsOfYear.firstId =/= a.id) idToVal(a.id - 1) else idToVal(MonthsOfYear.lastId)

    override def min: Option[MonthsOfYear.Month] = Some(JANUARY)

    override def max: Option[MonthsOfYear.Month] = Some(DECEMBER)

    override def order(x: Month, y: Month): Ordering = x.id ?|? y.id
  }

  implicit val monthShow: Show[Month] = new Show[Month] {}


  "succ returns expected months" in {
    assert(JANUARY.succ ≟ FEBRUARY)
    assert(DECEMBER.succ ≟ JANUARY)
  }

  "pred returns expected months" in {
    assert(JANUARY.pred ≟ DECEMBER)
    assert(DECEMBER.pred ≟ NOVEMBER)
  }

  "|-> " in {
    JUNE |-> AUGUST assert_=== JUNE::JULY::AUGUST::Nil
  }

  "fff" in {
    AUGUST |-> JUNE assert_=== AUGUST::JULY::JUNE::Nil
  }

  "fff2" in {
    FEBRUARY |--> (2, JULY) assert_=== FEBRUARY::APRIL::JUNE::Nil
  }

  "fff3" in {
    FEBRUARY |--> (-2, JULY) assert_=== FEBRUARY::DECEMBER::OCTOBER::AUGUST::Nil
  }

  "predx" in {
    println((Int.MinValue + 1).predx)

    println(JANUARY.predx)
  }

  "predx2" in {
    println(JANUARY |--> (2, NOVEMBER))
  }

  "predx return none for min" in {
    val intEnum = implicitly[Enum[Int]]
    intEnum.min assert_=== Int.MinValue.some
    Int.MinValue.predx assert_=== none[Int]

    val monthEnum = implicitly[Enum[Month]]
    monthEnum.min assert_=== JANUARY.some
    JANUARY.predx assert_=== none[Month]
  }

  "succx return none for max" in {
    val intEnum = implicitly[Enum[Int]]
    intEnum.max assert_=== Int.MaxValue.some
    Int.MaxValue.succx assert_=== none[Int]

    val monthEnum = implicitly[Enum[Month]]
    monthEnum.max assert_=== DECEMBER.some
    DECEMBER.succx assert_=== none[Month]
  }

  "preddx2" in {
    println(110.toByte |--> (2, 127.toByte))
//    println(128.toByte.succ)
  }
}
