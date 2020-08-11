package scells

import scala.util.parsing.combinator.RegexParsers
import alphacolumn._

object FormulaParsers extends RegexParsers {
  def identifier: Parser[String] = """[a-zA-Z_]\w*""".r
  def decimal: Parser[String] = """-?\d+(\.\d*)?""".r

  def cell: Parser[Coord] = 
    """[A-Za-z]+\d+""".r ^^ { s =>
      val columnString = "[A-Za-z]+".r findFirstIn s
      columnString match {
        case Some(str) => {
          
          val row = s.substring(str.length).toInt
          Coord(row, str.toUpperCase.toColumn)
        }
        case None => throw new IllegalArgumentException
      }
    }
}