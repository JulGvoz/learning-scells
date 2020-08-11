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
          println("Parsing " + str)
          val columnStringLength = str.length
          println("Length: " + columnStringLength)
          println((columnStringLength until 0 by -1))
          val column = (for ((c, i) <- str zip (columnStringLength until 0 by -1)) yield {
            (c.toUpper - 'A')*math.pow(26, i - 1).toInt
          }).sum
          println("Resulting value: " + column)
          val row = s.substring(columnStringLength).toInt
          Coord(row, column)
        }
        case None => throw new IllegalArgumentException
      }
    }
}