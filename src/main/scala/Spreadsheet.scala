package scells

import scala.swing._

class Spreadsheet(val height: Int, val width: Int)
    extends ScrollPane {
  
  val table = new Table(height, width) {
    rowHeight = 25
    autoResizeMode = Table.AutoResizeMode.Off
    showGrid = true
    gridColor = new java.awt.Color(150, 150, 150)
  }

  val rowHeader = 
    new ListView((0 until height) map (_.toString)) {
      fixedCellHeight = table.rowHeight
      fixedCellWidth = 30
    }
  
  viewportView = table
  rowHeaderView = rowHeader
}