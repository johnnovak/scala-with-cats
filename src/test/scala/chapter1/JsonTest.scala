package chapter1

import org.scalatest._

class JsonTest extends WordSpec with MustMatchers {
  
  "Json" must {

    "interface objects" in {
      import JsonWriterInstances._

      val res = Json.toJson(Person("Dave", "dave@example.com"))
    }
    
    "extension methods" in {
      import JsonWriterInstances._
      import JsonSyntax._
      
      val res = Person("Dave", "dave@example.com").toJson
    }

    "implicitly" in {
      import JsonWriterInstances._
      import JsonSyntax._

      val w = implicitly[JsonWriter[String]]
    }

    "option writer" in {
      import JsonWriterInstances._
      import JsonOption._
      import JsonSyntax._
      
      val o1 = Json.toJson(Option("some string"))
      Json.toJson(Option("some string"))(optionWriter[String])
      Json.toJson(Option("some string"))(optionWriter(stringWriter))

      val o2 = Option("some string").toJson
    }
    
  }

}