package chapter4.ex

trait Monad[F[_]] {

  def pure[A](value: A): F[A]

  def flatMap[A, B](value: F[A])(f: A => F[B]): F[B]

  def map[A, B](stuff: F[A])(f: A => B): F[B] =
    flatMap(stuff)(f andThen pure)

}
