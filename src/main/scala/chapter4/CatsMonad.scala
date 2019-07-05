package chapter4

import cats.Monad
import cats.instances.option._  // for Monad
import cats.instances.list._    // for Monad

import scala.concurrent.{Await, Future}

object CatsMonad extends App {

  // Type classes
  {
    val opt1 = Monad[Option].pure(3)
    println(opt1)

    val opt2 = Monad[Option].flatMap(opt1)(a => Some(a + 2))
    println(opt2)

    val opt3 = Monad[Option].map(opt2)(a => 100 * a)
    println(opt3)

    val list1 = Monad[List].pure(3)
    println(list1)

    val list2 = Monad[List].flatMap(List(1, 2, 3))(a => List(a, a * 10))
    println(list2)

    val list3 = Monad[List].map(list2)(a => a + 123)
    println(list3)

    val list4 = Monad[List].map(List(1, 2, 3))(a => List(a, a * 10))
    println(list4)

    // Vector
    import cats.instances.vector._  // for Monad

    val vec = Monad[Vector].flatMap(Vector(1, 2, 3))(a => Vector(a, a * 10))
    println(vec)
  }

  // Future
  {
    import cats.instances.future._  // for Monad
    import scala.concurrent.ExecutionContext.Implicits.global
    import scala.concurrent.duration._

    val fm = Monad[Future]
    val f1 = fm.pure(1)
    val f2 = fm.flatMap(f1)(x => fm.pure(x + 2))

    println()
    println(Await.result(f2, 1.second))
  }

  // Syntax
  {
    import cats.syntax.applicative._  // for pure

    println()
    println(1.pure[Option])
    println(1.pure[List])
  }

  // Sum squares
  {
    import cats.syntax.functor._  // for map
    import cats.syntax.flatMap._  // for flatMap

    // Context bound syntax
    def sumSquare[F[_]: Monad](a: F[Int], b: F[Int]): F[Int] =
      a.flatMap(x => b.map(y => x*x + y*y))

    // Explicit syntax
    def sumSquareExp[F[_]](a: F[Int], b: F[Int])
                          (implicit monad: Monad[F]): F[Int] =
      a.flatMap(x => b.map(y => x*x + y*y))

    // For-comprehension syntax
    def sumSquareFor[F[_]: Monad](a: F[Int], b: F[Int]): F[Int] =
      for {
        x <- a
        y <- b
      } yield x*x + y*y

    println()
    println(sumSquare(   Option(2), Option(4)))
    println(sumSquareExp(Option(2), Option(4)))
    println(sumSquareFor(Option(2), Option(4)))

    println(sumSquare(   List(1, 2, 3), List(4, 5)))
    println(sumSquareExp(List(1, 2, 3), List(4, 5)))
    println(sumSquareFor(List(1, 2, 3), List(4, 5)))
  }

}
