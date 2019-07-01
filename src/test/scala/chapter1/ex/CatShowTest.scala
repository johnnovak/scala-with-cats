package chapter1.ex

import org.scalatest._

class CatShowTest extends WordSpec with MustMatchers {
  
  "CatShow" must {

    "print a cat correctly" in {
      import cats.implicits._
      import CatShow._

      val cat = Cat(name = "Eddie", age = 5, color = "brown")
      
      val s = cat.show
      println(s)
    }
    
  }

}