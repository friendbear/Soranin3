import scala.beans.BeanInfo

/**
  * Created by kumagai on 2016/06/17.
  */
object JSONExample extends App {

  //import sjson.json.Serializer.SJSON

  // ケースクラスをJSON(バイト配列に変換
  //val json: Array[Byte] = SJSON.out(Person("Tomohiro", List("Java", "Scala")))
  //println(json)
}

@BeanInfo
case class Person (name: String, lang: List[String]) {
  private def this() = this(null, null)
}
