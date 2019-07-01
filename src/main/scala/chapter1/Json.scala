package chapter1

sealed trait Json
final case class JsObject(get: Map[String, Json]) extends Json
final case class JsString(get: String) extends Json
final case class JsNumber(get: Double) extends Json
case object JsNull extends Json

trait JsonWriter[A] {
  def write(value: A): Json
}

// Type class instances
// --------------------
final case class Person(name: String, email: String)

object JsonWriterInstances {

  implicit val stringWriter: JsonWriter[String] =
    new JsonWriter[String] {
      def write(s: String) = JsString(s)
    }
  
  implicit val personWriter: JsonWriter[Person] =
    new JsonWriter[Person] {
      def write(p: Person) = JsObject(Map(
        "name" -> JsString(p.name),
        "email" -> JsString(p.email)
      ))
    }
}

// Type class interfaces
// ---------------------
// 1. Interface objects
object Json {
  def toJson[A](value: A)(implicit w: JsonWriter[A]) =
    w.write(value)
}

// 2. Interface syntax (extension method)
object JsonSyntax {
  implicit class JsonWriterOps[A](value: A) {
    def toJson(implicit w: JsonWriter[A]): Json = 
      w.write(value)
  }
}

// Recursive implicit resolution
// -----------------------------
object JsonOption {

  implicit def optionWriter[A](implicit writer: JsonWriter[A]): JsonWriter[Option[A]] =
    new JsonWriter[Option[A]] {
      def write(option: Option[A]): Json =
        option match {
          case Some(value) => writer.write(value)
          case None => JsNull
        }
    }
}
