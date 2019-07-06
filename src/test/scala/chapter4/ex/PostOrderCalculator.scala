package chapter4.ex

import cats._
import cats.data.State
import cats.implicits._

import scala.annotation.tailrec


object PostOrderCalculator extends App {

  type CalcState[A] = State[List[Int], A]

  def evalOne(sym: String): CalcState[Int] =
    State[List[Int], Int] { currStack =>

      def operator(f: (Int, Int) => Int) = currStack match {
        case b :: a :: tail =>
          val ans = f(a, b)
          (ans :: tail, ans)
        case _ =>
          sys.error("Fail!")
      }

      sym match {
        case "+" => operator(_ + _)
        case "-" => operator(_ - _)
        case "*" => operator(_ * _)
        case "/" => operator(_ / _)
        case num =>
          val n = num.toInt
          (n :: currStack, n)
      }
    }

  println(
    evalOne("42").runA(Nil).value
  )
  println(
    evalOne("+").runA(List(2, 1)).value
  )
  println(
    evalOne("-").runA(List(-2, 1)).value
  )

  {
    val program = for {
      _ <- evalOne("1")
      _ <- evalOne("2")
      ans <- evalOne("+")
    } yield ans

    println("-" * 80)
    println(
      program.runA(Nil).value
    )
  }

  def evalAll(input: List[String]): CalcState[Int] = {

    @tailrec
    def loop(in: List[String], state: CalcState[Int]): CalcState[Int] = {
      in match {
        case Nil          => state
        case head :: Nil  => state.flatMap(_ => evalOne(head))
        case head :: tail =>
          val newState = state.flatMap(_ => evalOne(head))
          loop(tail, newState)
      }
    }

    loop(input, 0.pure[CalcState])
  }

  println("-" * 80)
  println(
    evalAll(List("1", "2", "+", "3", "*")).runA(Nil).value
  )

  {
    val program = for {
      _   <- evalAll(List("1", "2", "+"))
      _   <- evalAll(List("3", "4", "+"))
      ans <- evalOne("*")
    } yield ans

    println("-" * 80)
    println(
      program.runA(Nil).value
    )
  }

  def evalInput(s: String): Int = {
    val input = s.split(" ").toList
    val program = evalAll(input)
    program.runA(Nil).value
  }

  println(evalInput("1 2 + 3 4 + *"))

}
