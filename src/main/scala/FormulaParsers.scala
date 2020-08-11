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
  
  def range: Parser[Range] = cell~":"~cell ^^ {
    case c1~":"~c2 => Range(c1, c2)
  }

  def number: Parser[Number] =
    decimal ^^ (d => Number(d.toDouble))
  
  def application: Parser[Application] = 
    identifier~"("~repsep(expr, ",")~")" ^^ {
      case f~"("~ps~")" => Application(f, ps)
    }
  
  def expr: Parser[Formula] = 
    range | cell | number | application
  
  def textual: Parser[Textual] = 
    """[^=].*""".r ^^ Textual
  
  def formula: Parser[Formula] =
    number | textual | "="~>expr
  
  def parse(input: String): Formula = 
    parseAll(formula, input) match {
      case Success(result, next) => result
      case f: NoSuccess => Textual("["+ f.msg + "]")
    }
}