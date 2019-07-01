package chapter2.cats

object CatsMonoid extends App {
  
  import cats.Monoid
  import cats.instances.string._

  val res1 = Monoid[String].combine("Hi ", "there")
  println(res1)

  //---------------------------------------------------
  import cats.Semigroup

  val res2 = Semigroup[String].combine("Hi ", "there")
  println(res2)
  
  println(Monoid[String].empty)
 
  //---------------------------------------------------
  import cats.instances.int._
  
  val res3 = Monoid[Int].combine(40, 2)
  println(res3)
  
  //---------------------------------------------------
  import cats.syntax.semigroup._
  
  val stringRes = "Hi " |+| "there" |+| Monoid[String].empty
  println(stringRes)
  
  val intRes = 30 |+| 10 |+| 2 |+| Monoid[Int].empty
  println(intRes)

}