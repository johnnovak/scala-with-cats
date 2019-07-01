package chapter1.ex

import cats.Eq
import cats.syntax.eq._
import cats.instances.int._
import cats.instances.string._

object CatEq {
  
  implicit val catEq = Eq.instance[Cat] { (c1, c2) => {
    c1.name  === c2.name  &&
    c1.age   === c2.age   &&
    c1.color === c2.color
  }}
  
}