package scells

trait Evaluator {
  this: Model =>

  def evaluate(e: Formula): Double = try { 
    e match {
    case Number(v) => v
    case Coord(row, column) => cells(row)(column).value
    case Textual(_) => 0
    case Application(function, arguments) => 
      val argvals = arguments flatMap evalList
      operations(function)(argvals)
    }
  } catch {
    case ex: Exception => Double.NaN
  }

  def evalList(e: Formula): List[Double] = e match {
    case Range(_, _) => references(e) map (_.value)
    case _ => List(evaluate(e))
  }

  type Op = List[Double] => Double
  val operations = new collection.mutable.HashMap[String, Op]

  def references(e: Formula): List[Cell] = e match {
    case Coord(row, column) => List(cells(row)(column))
    case Range(Coord(r1, c1), Coord(r2, c2)) =>
      for (
        row <- (r1 to r2).toList;
        column <- c1 to c2
      ) yield cells(row)(column)
    case Application(function, arguments) => 
      arguments flatMap references
    case _ => Nil
  }
}