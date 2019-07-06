package chapter4.ex


object TreeMonad extends App {

  sealed trait Tree[+A]

  final case class Branch[A](left: Tree[A], right: Tree[A]) extends Tree[A]

  final case class Leaf[A](value: A) extends Tree[A]

  def branch[A](left: Tree[A], right: Tree[A]): Tree[A] =
    Branch(left, right)

  def leaf[A](value: A): Tree[A] =
    Leaf(value)

  val treeMonad = new Monad[Tree]{
    def pure[A](a: A): Tree[A] = Leaf(a)

    def flatMap[A, B](tree: Tree[A])(fn: A => Tree[B]): Tree[B] =
      tree match {
        case Branch(l, r) => Branch(flatMap(l)(fn), flatMap(r)(fn))
        case Leaf(value)  => fn(value)
      }

    def tailRecM[A, B](a: A)(fn: A => Tree[Either[A, B]]): Tree[B] =
      flatMap(fn(a)) {
        case Left(v)  => tailRecM(v)(fn)
        case Right(v) => Leaf(v)
      }

  }
}
