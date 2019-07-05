package chapter4

import cats._
import cats.implicits._

object EvalMonad extends App {

  val now = Eval.now(math.random + 1000)
  println(now)

  val later = Eval.later(math.random + 2000)
  println(later)

  val always = Eval.always(math.random + 2000)
  println(always)

  println("now", now.value, now.value)
  println("later", later.value, later.value)
  println("always", always.value, always.value)

  println("-------------------------------------------------------------------")

  val greeting = Eval
    .always { println("Step 1"); "Hello" }
    .map { s => println("Step 2"); s"$s world" }

  println(greeting)
  println()
  println(greeting.value)
  println()
  println(greeting.value)

  println("-------------------------------------------------------------------")

  val ans = for {
    a <- Eval.now { println("Calculating A"); 40 }
    b <- Eval.always { println("Calculating B"); 2 }
  } yield {
    println("Adding A and B")
    a + b
  }

  println(ans)
  println()
  println(ans.value)
  println()
  println(ans.value)

  println("-------------------------------------------------------------------")

  val saying = Eval
    .always { println("Step 1"); "The cat" }
    .map { s => println("Step 2"); s"$s sat on" }
    .memoize
    .map { s => println("Step 3"); s"$s the mat" }

  println(saying)
  println()
  println(saying.value)
  println()
  println(saying.value)

  // Trampolining

  def fac(n: BigInt): BigInt =
    if (n == 1) 1
    else n * fac(n - 1)

  // fac(50000)    // ERROR: stack overflow

  def facSafe(n: BigInt): Eval[BigInt] =
    if (n == 1) Eval.now(1)
    else Eval.defer(facSafe(n - 1)).map(_ * n)

  println(facSafe(50000).value)
}

