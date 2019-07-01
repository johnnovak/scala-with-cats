package chapter1.ex

object CatEqTest extends App {

  import CatEq.catEq
  import cats.syntax.eq._

  val cat1 = Cat("Garfield", 38, "orange and black")
	val cat2 = Cat("Heathcliff", 33, "orange and black")

	println(cat1 === cat2)
	println(cat1 =!= cat2)
	println(cat1 === cat1)
	println(cat1 =!= cat1)
  println
	
	import cats.instances.option._
	
	val optionCat1 = Option(cat1)
	val optionCat2 = Option.empty[Cat]

	println(optionCat1 === optionCat2)
	println(optionCat1 =!= optionCat2)
	println(optionCat1 === optionCat1)
	println(optionCat1 =!= optionCat1)

}