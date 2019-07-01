package chapter1.ex

import org.scalatest._

class PrintableTest extends WordSpec with MustMatchers {
  
  "Printable" must {

    "print a cat correctly" in {
      import PrintableInstances._
      import PrintableSyntax._

      val cat = Cat(name = "Eddie", age = 5, color = "brown")
      
      Printable.print(cat)
      cat.print
    }
    
  }

}