package chapter4.ex

import cats._
import cats.data.Reader
import cats.implicits._


object HackingOnReaders extends App {

  case class Db(
    usernames: Map[Int, String],
    passwords: Map[String, String]
  )

  type DbReader[A] = Reader[Db, A]

  def findUserName(userId: Int): DbReader[Option[String]] =
    Reader(db => db.usernames.get(userId))

  def checkPassword(username: String, password: String): DbReader[Boolean] =
    Reader(db => db.passwords.get(username).contains(password))

  def checkLogin(userId: Int, password: String): DbReader[Boolean] =
    for {
      username   <- findUserName(userId)
      passwordOk <- username.map { checkPassword(_, password) }
                            .getOrElse(false.pure[DbReader])
    } yield passwordOk


  val users = Map(
    1 -> "dade",
    2 -> "kate",
    3 -> "margo"
  )

  val passwords = Map(
    "dade"  -> "zerocool",
    "kate"  -> "acidburn",
    "margo" -> "secret"
  )

  val db = Db(users, passwords)

  println(
    checkLogin(1, "zerocool").run(db)
  )
  println(
    checkLogin(4, "davinci").run(db)
  )

}
