package chapter2.cats.ex

import cats._
import cats.implicits._


object SuperAdder extends App {

  def add[A](items: List[A])(implicit monoid: Monoid[A]): A =
    items.foldLeft(monoid.empty)(_ |+| _)

  // Using context bounds syntax
  def add2[A: Monoid](items: List[A]): A =
    items.foldLeft(Monoid[A].empty)(_ |+| _)

  println(add(List(1, 2, 3)))
  println(add2(List(1, 2, 3)))
  println(add(List(Some(1), None, Some(2), None, Some(3))))
  println(add2(List(Some(1), None, Some(2), None, Some(3))))

//  add(List(Some(1), Some(2), Some(3)))  // ERROR (because the inferred type is List[Some])


  case class Order(totalCost: Double, quantity: Double)

  implicit val orderMonoid = new Monoid[Order] {
    def combine(x: Order, y: Order): Order =
      Order(
        totalCost = x.totalCost + y.totalCost,
        quantity = x.quantity + y.quantity
      )

    def empty: Order = Order(0, 0)
  }


  val order1 = Order(totalCost = 200.0, quantity = 4)
  val order2 = Order(totalCost = 150.0, quantity = 3)
  val totalOrders = add(List(order1, order2))
  println(totalOrders)

}