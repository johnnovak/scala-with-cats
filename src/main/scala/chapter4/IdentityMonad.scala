package chapter4

import cats._
import cats.implicits._

object IdentityMonad extends App {

  def sumSquare[F[_]: Monad](a: F[Int], b: F[Int]): F[Int] =
    for {
      x <- a
      y <- b
    } yield x*x + y*y

  println(sumSquare(3 : Id[Int], 4 : Id[Int]))

  val dave1 = "Dave"
  val dave2: Id[String] = "Dave"
  println(dave1 == dave2)
  println(dave1 eq dave2)
  println(System.identityHashCode(dave1))
  println(System.identityHashCode(dave2))

  val a = Monad[Id].pure(3)
  val b = Monad[Id].flatMap(a)(_ + 1)
  println(a, b)
  println(sumSquare(a, b))

}
