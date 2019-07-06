package chapter4

import cats.data.State


object StateMonad extends App {

  {
    val a = State[Int, String] { state =>
      (state, s"The state is $state")
    }

    val (state, answer) = a.run(10).value
    println(state, answer)

    println(a.runS(20).value)

    println(a.runA(30).value)
  }

  // Sequencing operations
  {
    val step1 = State[Int, String] { n =>
      val res = n + 1
      (res, s"Result of step1: $res")
    }

    val step2 = State[Int, String] { n =>
      val res = n * 2
      (res, s"Result of step2: $res")
    }

    val both = for {
      a <- step1
      b <- step2
    } yield (a, b)

    val (state, answer) = both.run(10).value
    println(state, answer)
  }

  // Helper methods
  {
    println()

    val getDemo = State.get[Int]
    println(getDemo.run(10).value)

    val setDemo = State.set[Int](30)
    println(setDemo.run(10).value)

    val pureDemo = State.pure[Int, String]("Result")
    println(pureDemo.run(10).value)

    val inspectDemo = State.inspect[Int, String](_ + "!")
    println(inspectDemo.run(10).value)

    val modifyDemo = State.modify[Int](_ + 1)
    println(modifyDemo.run(10).value)

    val program = for {
      a <- State.get[Int]
      _ <- State.set[Int](a + 1)
      b <- State.get[Int]
      _ <- State.modify[Int](_ + 1)
      c <- State.inspect[Int, Int](_ * 1000)
    } yield (a, b, c)

    println(program.run(1).value)
  }

}
