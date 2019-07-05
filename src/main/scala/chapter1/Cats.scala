package chapter1

object Cats {

  // Default instances
  {
    import cats.Show
    import cats.instances.int._
    import cats.instances.string._
//    import cats.instances.all._

    val showInt    = Show.apply[Int]
    val showString = Show.apply[String]

    val intAsString: String = showInt.show(123)
    val stringAsString: String = showString.show("abc")
  }

  // Interface syntax
  {
    import cats.instances.int._
    import cats.instances.string._
    import cats.syntax.show._

    val showInt = 123.show
    val showString = "abc".show
  }

  // Import all syntax
  {
    import cats._
    import cats.implicits._

    val showInt    = Show.apply[Int]
    val showString = Show.apply[String]

    val intAsString: String = showInt.show(123)
    val stringAsString: String = showString.show("abc")

    val s = 123.show
    val i = "abc".show
  }

  // Defining custom instance
  {
    import cats._
    import cats.implicits._
    import java.util.Date

    implicit val dateShow: Show[Date] =
      new Show[Date] {
        def show(date: Date): String =
          s"${date.getTime} ms since the epoch."
      }

    val d = new Date
    d.show
  }

  // Defining custom instance (short version)
  {
    import cats._
    import cats.implicits._
    import java.util.Date

    implicit val dateShowShort: Show[Date] =
      Show.show(date => s"${date.getTime} ms since the epoch.")

    val d = new Date
    d.show
  }

  // Type-safe equality
  {
    import cats.Eq
    import cats.instances.int._
    import cats.syntax.eq._

    val eqInt = Eq[Int]

    eqInt.eqv(123, 456)
//    eqInt.eqv(123, "abc")   // ERROR

    123 === 123
    123 =!= 234
//    123 === "abc"  // ERROR
  }

  // Type-safe equality for options
  {
    import cats.Eq
    import cats.instances.int._
    import cats.instances.option._
    import cats.syntax.eq._

//    Some(1) === None   // ERROR
    (Some(1): Option[Int]) === (None: Option[Int])
    Option(1) === Option.empty[Int]

    import cats.syntax.option._

    1.some === none[Int]
    1.some =!= none[Int]
  }

  // Comparing custom types
  {
    import cats.Eq
    import cats.instances.long._
    import cats.syntax.eq._
    import java.util.Date

    implicit val dateEq: Eq[Date] =
      Eq.instance[Date] { (date1, date2) =>
        date1.getTime === date2.getTime
      }

    val x = new Date
    val y = new Date

    x === x
    y === y
  }

}