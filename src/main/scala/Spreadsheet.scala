package scells

import scala.swing._

class Spreadsheet(val height: Int, val width: Int)
    extends ScrollPane {
  
  val cellModel = new Model(height, width)
  import cellModel._

  val table = new Table(height, width) {
    rowHeight = 25
    autoResizeMode = Table.AutoResizeMode.Off
    showGrid = true
    gridColor = new java.awt.Color(150, 150, 150)

    override protected def rendererComponent(isSelected: Boolean, focused: Boolean, row: Int, column: Int): Component = {
      if (focused) new TextField(userData(row, column))
      else new Label(cells(row)(column).toString) {
        xAlignment = Alignment.Right
      }
    }

    def userData(row: Int, column: Int): String = {
      val v = this(row, column)
      if (v == null) "" else v.toString
    }

  }

  val rowHeader = 
    new ListView((0 until height) map (_.toString)) {
      fixedCellHeight = table.rowHeight
      fixedCellWidth = 30
    }
  
  viewportView = table
  rowHeaderView = rowHeader
}

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