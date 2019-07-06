package chapter4

import cats._
import cats.data.Reader
import cats.implicits._


object ReaderMonad extends App {

  case class Cat(name: String, favouriteFood: String)

  val catName: Reader[Cat, String] =
    Reader(cat => cat.name)

  val cat1 = Cat("Garfield", "lasagne")

  println(catName.run(cat1))

  val greetKitty: Reader[Cat, String] =
    catName.map(name => s"Hello $name")

  val cat2 = Cat("Heathcliffe", "junk food")

  println(greetKitty.run(cat2))

  val feedKitty: Reader[Cat, String] =
    Reader(cat => s"Have a nice bowl of ${cat.favouriteFood}")

  val greetAndFeed: Reader[Cat, String] =
    for {
      greet <- greetKitty
      feed  <- feedKitty
    } yield s"$greet. $feed."

  println(greetAndFeed(cat1))
  println(greetAndFeed(cat2))
}
