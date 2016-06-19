import scalax.file.{Path, PathSet}
import scalax.io.{Codec, Resource}
/**
  * Created by kumagai on 2016/06/16.
  */
object ScalaIOExample extends App {

  implicit val codec = Codec("UTF-8")
  // 144 ファイルやディレクトリを作成する
  val filePath = Path("hoge.txt" )
  filePath.createFile(failIfExists = false) // IOExceptionを発生させないためのパラメータ

  filePath.deleteIfExists()
  //filePath.createDirectory() // ディレクトリの作成

  // 147 ディレクトリのファイルやディレクトリを取得
  val children: PathSet[Path] = Path("/").children().filter {_.isFile }
  children.foreach({path =>
    path.isDirectory match {
      case true => printAttributes(path)
      case false => printAttributes(path)
    }

  })

  // 148 親ディレクトリを取得
  //カレントディレクトリの絶対パスを取得
  val path = Path().toAbsolute
  println(path.toURL)
  val parent: Option[Path] = path.toAbsolute.parent
  parent match {
    case Some(x) => println(x.path)
    case None => println("parent directory does not exist.")
  }

  // 149 ファイルやディレクトリの属性を調べる
  def printAttributes(path: Path): Unit = {
    println("Path:%s".format(path))
    println("ファイルかどうか:" + path.isFile)
    println("ディレクトリかどうか:" + path.isDirectory)
    println("隠しファイルかどうか:" + path.isHidden)
    println("シンボリックリンクかどうか:" + path.isSymlink)
    println("読み取り可能かどうか:" + path.canRead)
    println("書き込み可能かどうか:" + path.canWrite)
    println("実行可能かどうか:" + path.canExecute)
    println("存在するかどうか:" + path.exists)
    println("最終更新日時:" + new java.util.Date(path.lastModified))
    println("ファイルサイズ:" + path.size)
    println("アクセスモード:" + path.access)

    // 150
    import scalax.file.ImplicitConversions.string2path
    val path150: Path = "foo/bar/hoge.txt"

    // 151 ファイルに対して文字列の読み書きを行う
    val path151 = Path("huga.txt")
    // TODO: コンパイルエラー val str = path151.slurpString
    path151.write("151のテスト")
    path151.append("151のテストappend")

    val resource = Resource.fromFile("path151")
    // TODO: コンパイルエラー println(resource.slurpString)

  }
}
