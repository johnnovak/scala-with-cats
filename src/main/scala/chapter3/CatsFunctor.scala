package chapter3


object CatsFunctor extends App {

  {
    import cats.Functor
    import cats.instances.list._
    import cats.instances.option._

    val list1 = List(1, 2, 3)
    val list2 = Functor[List].map(list1)(_ * 2)
    println(list2)

    val option1 = Option(123)
    val option2 = Functor[Option].map(option1)(_.toString)
    println(option2)

    val func = (x: Int) => x + 1
    val liftedFunc = Functor[Option].lift(func)
    liftedFunc(option1)
  }

  println("-----------------------------------------------------")

  {
    import cats.Functor
    import cats.instances.function._
    import cats.syntax.functor._

    val func1 = (a: Int) => a + 1
    val func2 = (a: Int) => a * 2
    val func3 = (a: Int) => a + "!"
    val func4 = func1 map func2 map func3

    println(func4(123))

    def doMath[F[_]](start: F[Int])
                    (implicit functor: Functor[F]): F[Int] =
      start.map(_ + 1 * 2)

    import cats.instances.list._
    import cats.instances.option._

    println(doMath(List(1, 2, 3)))
    println(doMath(Option(20)))

  }

}
