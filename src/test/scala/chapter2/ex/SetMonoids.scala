package chapter2.ex

object SetMonoids {

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

  implicit def setUnionMonoid[A] = new Monoid[Set[A]] {
    def combine(x: Set[A], y: Set[A]) = x union y
    def empty = Set.empty[A]
  }
  
  val intSetMonoid = Monoid[Set[Int]]
  val stringSetMonoid = Monoid[Set[String]]
  
  implicit def setIntersectionSemigroup[A] = new Semigroup[Set[A]] {
    def combine(x: Set[A], y: Set[A]) = x intersect y
  }
  
}
