package chapter4.ex

import cats.Id

object MonadicSecretIdentities extends App {

  def pure[A](fa: A): Id[A] = fa

  def map[A, B](fa: Id[A])(f: A => B): Id[B] = f(fa)

  def flatMap[A, B](fa: Id[A])(f: A => Id[B]): Id[B] = f(fa)

  val s1 = pure("Orange")
  val s2 = map(s1)(s => s + "s")
  val s3 = map(s1)(s => s + "s")

  println(s1, s2, s3)

}
