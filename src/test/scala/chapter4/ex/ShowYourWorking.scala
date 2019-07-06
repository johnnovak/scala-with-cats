package chapter4.ex

import cats._
import cats.data.Writer
import cats.implicits._
import scala.concurrent.{Await, Future}
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration._


object ShowYourWorking extends App {

  def slowly[A](body: => A) =
    try body finally Thread.sleep((math.random() * 1000).toInt)

  def fact(n: Int): Int = {
    val ans = slowly {
      if (n == 0) 1
      else n * fact(n - 1)
    }
    println(s"fact $n $ans")
    ans
  }

//  Await.result(
//    Future.sequence(Vector(
//      Future(fact(3)),
//      Future(fact(4))
//    )),
//    30.seconds
//  )

  type Logged[A] = Writer[Vector[String], A]

  def logFact(n: Int): Logged[Int] =
    for {
      ans <- if (n == 0) 1.pure[Logged]
             else slowly {
               logFact(n - 1).map(_ * n)
             }
      _   <- Vector(s"fact $n $ans").tell
    } yield ans

  val futures = Vector(
    Future(logFact(3)),
    Future(logFact(4)),
    Future(logFact(5)),
    Future(logFact(6)),
    Future(logFact(7))
  )
  println("Futures created")
  Thread.sleep(10 * 1000)

  // Note that Await will return immediately because the futures were
  // started on creation.

  println("Await...")
  val res = Await.result(Future.sequence(futures), 30.seconds)
  println("Done")

  res.foreach { r => println(r.written) }

}
