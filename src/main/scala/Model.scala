package scells
import swing._

class Model(height: Int, width: Int) extends Evaluator with Arithmetic {
  case class ValueChanged(cell: Cell) extends swing.event.Event

  case class Cell(row: Int, column: Int) extends Publisher {
    override def toString: String = formula match {
      case Textual(s) => s
      case _ => value.toString
    }

    private var f: Formula = Empty
    def formula: Formula = f
    def formula_=(n: Formula) = {
      for (c <- references(f)) deafTo(c)
      f = n
      for (c <- references(n)) listenTo(c)
      value = evaluate(f)
    }

    private var v: Double = 0
    def value: Double = v
    def value_=(w: Double) = {
      if (!(v == w || v.isNaN && w.isNaN)) {
        v = w
        publish(ValueChanged(this))
      }
    }

    reactions += {
      case ValueChanged(_) => value = evaluate(formula)
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