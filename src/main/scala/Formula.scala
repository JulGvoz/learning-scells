package scells

trait Formula

case class Coord(row: Int, var column: Int) extends Formula {
  override def toString = {
    var columnStr = ""
    do {
      println(column)
      columnStr += (column % 26 + 'A').toChar.toString
      column = column / 26
    } while (column != 0)
    columnStr.reverse + row.toString
  }
}

case class Range(c1: Coord, c2: Coord) extends Formula {
  override def toString = c1.toString + ":" + c2.toString
}

case class Number(value: Double) extends Formula {
  override def toString = value.toString
}

case class Textual(value: String) extends Formula {
  override def toString = value
}

case class Application(function: String, arguments: List[Formula]) extends Formula {
  override def toString = function + arguments.mkString("(", ", ", ")")
}

object Empty extends Textual("")