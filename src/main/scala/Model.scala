package scells

class Model(height: Int, width: Int) extends Evaluator with Arithmetic {
  case class Cell(row: Int, column: Int) {
    var formula: Formula = Empty
    override def toString: String = formula match {
      case Textual(s) => s
      case _ => value.toString
    }
    def value: Double = evaluate(formula)
  }

  val cells = Array.ofDim[Cell](height, width)

  for {
    i <- 0 until height
    j <- 0 until width
  } {
    cells(i)(j) = new Cell(i, j)
  }
}