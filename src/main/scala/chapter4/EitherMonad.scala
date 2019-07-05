package chapter4

object EitherMonad extends App {

  // Supported in Scala 2.12+
  {
    val either1: Either[String, Int] = Right(10)
    val either2: Either[String, Int] = Right(32)

    val res = for {
      a <- either1
      b <- either2
    } yield a + b
  }

  // Syntax
  {
    import cats.implicits._

    val a = 3.asRight[String]
    val b = 4.asRight[String]

    val res = for {
      x <- a
      y <- b
    } yield x*x + y*y

    println(res)

    // Avoiding type narrowing
    def countPositive(nums: List[Int]): Either[String, Int] =
      nums.foldLeft(0.asRight[String]) { (acc, n) =>
        if (n > 0) acc.map(_ + 1)
        else Left("Non-positive. Stopping!")
      }

    println(countPositive(List(1, 2, 3)))
    println(countPositive(List(1, -2, 3)))

    // Extension methods
    println(
      Either.catchOnly[NumberFormatException]("foo".toInt)
    )
    println(
      Either.catchNonFatal(sys.error("disaster"))
    )

    // Transforms
    val e1 = "Error".asLeft[Int].getOrElse(-1)
    println(e1)

    val e2 = "Error".asLeft[Int].orElse(2.asRight[String])
    println(e2)

    val e3 = (-1).asRight[String].ensure("Must be non-negative!")(_ > 0)
    println(e3)

    val e4 ="error".asLeft[Int].recover {
      case _: String => -42
    }
    println(e4)

    val e5 ="error".asLeft[Int].recoverWith {
      case _: String => Left("unrecoverable")
    }
    println(e5)

    val e6 ="foo".asLeft[Int].leftMap(_.reverse)
    println(e6)
  }

  // Example: error handling
  {
    import cats.implicits._

    val res = for {
      a <- 1.asRight[String]
      b <- 0.asRight[String]
      c <- if (b == 0) "DIV0".asLeft[Int]
           else (a / b).asRight[String]
    } yield c * 100

    println(res)
  }

}
