
/**
  * Created by kumagai on 2016/06/19.
  */
object UtilExample extends App {

  // 167 日付処理を行う
//  import org.scala_tools.time.Imports._
/* TODO: SBTでコンパイルエラーになるんですけど
  // 現在日時の取得
  val dateTime1 = DateTime.now
  //現在日の 02:24:10のDateTimeを取得
  val dateTime2 = DateTime.now.hour(2).minute(45).second(10)

  // 現在日時の２ヶ月後のDateTimeを取得
  val dateTime3 = DateTime.now + 2.months
  println(dateTime1, dateTime2, dateTime3,
    DateTime.nextMonth < DateTime.now + 2.months)
*/

  // 169 パーサコンビネータ パーサ（関数）を引数に取る高階関数
  val parser = new IniParser
  // INIファイルをパース（戻り値はParseResult[ASTSections]）
  val result = parser.parseAll(parser.sections,
    """
      |[db1]
      |driver=org.postgresql.Driver
      |url=jdbc:postgresql:/localhost/testdb1
      |user=postgres
      |password=ppostgres
      |
      |[db2]
      |driver=org.postgresql.Driver
      |url=jdbc:postgresql:/localhost/testdb2
      |user=postgres
      |password=ppostgres
    """.stripMargin)

  //ParseResultからASTSectionsを取得
  val root = result.get

  //ASTをマップに変換
  val map = root.sections.map { section =>
    (section.name.string -> section.properties.map { property =>
      (property.key.string -> property.value.string)})
  }.toMap

  println(map)
  println(map.get("db1")) // TODO: valueを簡単にとる方法がわからない

  // 168 HTTP通信
  import dispatch._, Defaults._
/*  val svc = url("http://bears.plala.jp/")
  val contents = Http(svc OK as.String)

  contents.foreach {str => println(str) }
*/
  case class Location(city: String, state: String)

  def weatherSvc(loc: Location) = {
    host("api.wunderground.com") / "api" / "5a7c66db0ba0323a" /
      "conditions" / "q" / loc.state / (loc.city + ".xml")
  }
  def weatherXml(loc: Location) =
    Http(weatherSvc(loc) OK as.xml.Elem)

  val nyc = Location("Tokyo", "NY")
//  for (str <- Http(weatherSvc(nyc) OK as.String))
//    println(str)
  def printer = new scala.xml.PrettyPrinter(90, 2)
  for (xml <- weatherXml(nyc))
    println(printer.format(xml))
}

// 169 パーサコンビネータ
trait AST
// 文字列
case class ASTString(string: String) extends AST
// プロパティ
case class ASTProperty(key: ASTString, value: ASTString) extends AST
// セクション
case class ASTSection(name: ASTString, properties: List[ASTProperty]) extends AST
// セクションの集合（ファイル全体）
case class ASTSections(sections: List[ASTSection]) extends AST

import scala.util.parsing.combinator.RegexParsers
class IniParser extends RegexParsers {

  // 文字列（[、]、=、空白）を含まない
  def string :Parser[ASTString] = """[^\[\]=\s]*""".r^^{ //^^ perser1の解析結果をperser2で変換する
    case value => ASTString(value)
  }
  // property
  def property :Parser[ASTProperty] = string~"="~string^^{
    case (key~_~value) => ASTProperty(key, value)
  }
  // セクション
  def section :Parser[ASTSection] = "["~>string~"]"~rep(property)^^{
    case (section~_~properties) => ASTSection(section, properties)
  }
  //セクションの集合
  def sections :Parser[ASTSections] = rep(section)^^{
    case sections => ASTSections(sections)
  }
}
