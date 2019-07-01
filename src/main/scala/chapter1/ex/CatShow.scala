package chapter1.ex

import cats._
import cats.implicits._

object CatShow {
  
  implicit val catShow: Show[Cat] =
    Show.show(cat => {
      val name  = cat.name.show
      val age   = cat.age.show
      val color = cat.color.show
      s"$name is a $age-year-old $color cat."
    })
    
}