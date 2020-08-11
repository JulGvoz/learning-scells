package scells

class Model(height: Int, width: Int) {
  case class Cell(row: Int, column: Int) {
    override def toString: String = {
      Coord(row, column).toString
    }
  }

  val cells = Array.ofDim[Cell](height, width)

  for {
    i <- 0 until height
    j <- 0 until width
  } {
    cells(i)(j) = new Cell(i, j)
  }
}