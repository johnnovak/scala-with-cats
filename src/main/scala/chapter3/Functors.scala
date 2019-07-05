package chapter3

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration._
import scala.concurrent.{Await, Future}


object Functors extends App {

  println(
    List(1, 2, 3).map(n => n + 1)
  )

  //-------------------------------------------------
  println(
    List(1, 2, 3)
      .map(_ + 1)
      .map(_ * 1)
      .map(_ + "!")
  )

  //-------------------------------------------------
  val future = Future(123)
    .map(_ + 1)
    .map(_ * 2)
    .map(_ + "!")

  println(
    Await.result(future, 1.second)
  )

  //-------------------------------------------------
  import cats.instances.function._
  import cats.syntax.functor._

  val func1: Int => Double =
    (x: Int) => x.toDouble

  val func2: Double => Double =
    (y: Double) => y * 2

  println(
    (func1 map func2)(1)
  )
  println(
    (func1 andThen func2)(1)
  )
  println(
    func2(func1(1))
  )

}
