package chapter4

import cats._
import cats.data.Writer
import cats.implicits._

object WriterMonad extends App {

  type Logged[A] = Writer[Vector[String], A]

  {
    val w = Writer(Vector(
      "It was the best of times",
      "it was the worst of times"
    ), 1859)

    // Create from result only
    val wr1 = 123.pure[Logged]

    // Create from log only
    val wr2 = Vector("msg1", "msg2", "msg3").tell

    // Create from result & log
    val wr3 = Writer(Vector("msg1", "msg2", "msg3"), 123)
    val wr4 = 123.writer(Vector("msg1", "msg2", "msg3"))

    println(wr3.value)
    println(wr3.written)
    println(wr3.run)
  }

  // Composing & transforming Writers
  {
    val writer1 = for {
      a <- 10.pure[Logged]
      _ <- Vector("a", "b", "c").tell
      b <- 32.writer(Vector("x", "y", "z"))
    } yield a + b

    val res1 = writer1.run
    println(res1)

    val writer2 = writer1.mapWritten(_.map(_.toUpperCase))
    val res2 = writer2.run
    println(res2)

    val writer3 = writer1.bimap(
      log => log.map(_.toUpperCase),
      res => res * 100
    )
    val res3 = writer3.run
    println(res3)

    val writer4 = writer1.mapBoth { (log, res) => (log.map(_ + "!"), res * 1000) }
    val res4 = writer4.run
    println(res4)

    val writer5 = writer1.reset
    val res5 = writer5.run
    println(res5)

    val writer6 = writer1.swap
    val res6 = writer6.run
    println(res6)
  }

}
