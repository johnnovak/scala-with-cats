package chapter3.ex

import cats._
import cats.implicits._

object Tree extends App {

  sealed trait Tree[+A]

  final case class Branch[A](left: Tree[A], right: Tree[A]) extends Tree[A]

  final case class Leaf[A](value: A) extends Tree[A]

  object Tree {
    def branch[A](left: Tree[A], right: Tree[A]): Tree[A] =
      Branch(left, right)

    def leaf[A](value: A): Tree[A] =
      Leaf(value)
  }

  implicit val treeFunctor: Functor[Tree] = new Functor[Tree] {
    def map[A, B](value: Tree[A])(f: A => B): Tree[B] =
      value match {
        case Branch(l, r) => Branch(map(l)(f), map(r)(f))
        case Leaf(v)      => Leaf(f(v))
      }
  }

  val leaf1 = Tree.leaf(5)
  val mappedLeaf1 = leaf1.map(_.toString + "!")
  println(mappedLeaf1)

  val branch1 = Tree.branch(Leaf(1), Leaf(2))
  val mappedBranch1 = branch1.map(_ * 3)
  println(mappedBranch1)

}
