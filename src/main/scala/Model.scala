package scells

class Model(height: Int, width: Int) {
  case class Cell(row: Int, column: Int)

  val cells = Array.ofDim[Cell](height, width)

  for {
    i <- 0 until height
    j <- 0 until width
  } {
    cells(i)(j) = new Cell(i, j)
  }
}