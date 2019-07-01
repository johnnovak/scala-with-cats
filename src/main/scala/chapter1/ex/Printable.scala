package chapter1.ex

// Type class
trait Printable[A] {
  def format(value: A): String
}

final case class Cat(name: String, age: Int, color: String)

// Type class instances
object PrintableInstances {

  implicit val stringPrintable = new Printable[String] {
    def format(s: String) = s
  }

  implicit val intPrintable = new Printable[Int] {
    def format(i: Int) = i.toString
  }
  
  implicit val catPrintable = new Printable[Cat] {
    def format(c: Cat) = {
      val name  = Printable.format(c.name)
      val age   = Printable.format(c.age)
      val color = Printable.format(c.color)
      s"$name is a $age-year-old $color cat."
    }
  }
}

// Interface object
object Printable {
  
  def format[A](value: A)(implicit p: Printable[A]): String =
    p.format(value)
    
  def print[A](value: A)(implicit p: Printable[A]): Unit =
    println(format(value))
    
}

// Extension methods
object PrintableSyntax {
  
  implicit class PrintableOps[A](value: A) {
    def format(implicit p: Printable[A]): String =
      p.format(value)
      
    def print(implicit p: Printable[A]): Unit =
      println(format(p))
  }
}
