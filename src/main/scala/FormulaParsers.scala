package scells

import scala.util.parsing.combinator.RegexParsers

object FormulaParsers extends RegexParsers {
  def identifier: Parser[String] = """[a-zA-Z_]\w*""".r
  def decimal: Parser[String] = """-?\d+(\.\d*)?""".r

  def cell: Parser[Coord] = 
    """[A-Za-z]+\d+""".r ^^ { s =>
      val columnString = "[A-Za-z]+".r findFirstIn s
      columnString match {
        case Some(str) => {
          val columnStringLength = str.length
          val column = (for ((c, i) <- str zip (columnStringLength until 1)) yield {
            (c.toUpper - 'A')*math.pow(26, i).toInt
          }).sum
          val row = s.substring(columnStringLength).toInt
          Coord(row, column)
        }
        case None => throw new IllegalArgumentException
      }
    }
}