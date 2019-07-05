package chapter4.ex

import cats._

object SaferFolding extends App {

  def foldRight[A, B](as: List[A], acc: B)(fn: (A, B) => B): B =
    as match {
      case head :: tail => fn(head, foldRight(tail, acc)(fn))
      case Nil          => acc
    }

  def foldRightEval[A, B](as: List[A], acc: Eval[B])
                         (fn: (A, Eval[B]) => Eval[B]): Eval[B] =
    as match {
      case head :: tail => Eval.defer(fn(head, foldRightEval(tail, acc)(fn)))
      case Nil          => acc
    }

  def safeFoldRight[A, B](as: List[A], acc: B)(fn: (A, B) => B): B = {
    foldRightEval(as, Eval.now(acc)) { (a, b) =>
      b.map(fn(a, _))
    }.value
  }

  // println(foldRight((1 to 100000).toList, 0L)(_ + _))   // ERROR: stack overflow

  println(safeFoldRight((1 to 100000).toList, 0L)(_ + _))

}
