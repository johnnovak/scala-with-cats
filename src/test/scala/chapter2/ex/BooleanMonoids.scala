package chapter2.ex

object BooleanMonoids {
  
  trait Semigroup[A] {
    def combine(x: A, y: A): A
  }
  
  trait Monoid[A] {
    def empty: A
  }
  
  object Monoid {
    def apply[A](implicit monoid: Monoid[A]) =
      monoid
  }
  
  implicit val booleanAndMonoid = new Monoid[Boolean] {
    def combine(x: Boolean, y: Boolean) = x && y
    def empty = true
  }

  implicit val booleanOrMonoid = new Monoid[Boolean] {
    def combine(x: Boolean, y: Boolean) = x || y
    def empty = false
  }

  implicit val booleanXorMonoid = new Monoid[Boolean] {
    def combine(x: Boolean, y: Boolean) = x ^ y
    def empty = false
  }

  implicit val booleanXnorMonoid = new Monoid[Boolean] {
    def combine(x: Boolean, y: Boolean) = !(x ^ y)
    def empty = true
  }
  
}