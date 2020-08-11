import org.scalatest._
import flatspec._
import matchers._

import scells.FormulaParsers._
import scells._

class FormulaParsersSpec extends AnyFlatSpec with should.Matchers {
  "FormulaParsers.cell" should "be able to parse basic cells" in {
    val testCases = List("A1", "A2", "B1", "A0", "z0", "A13") zip 
                    List(Coord(1, 1), Coord(2, 1), Coord(1, 2), Coord(0, 1), Coord(0, 26), Coord(13, 1))
    
    for (test <- testCases) {
      assert(parseAll(cell, test._1).get == test._2, "(" + test._1 + " == " + test._2.toString + ")")
    }
  }

  it should "parse cells with longer column values" in {
    val testCases = List("A1", "AA2", "BA1", "AA0", "zA0", "AA13") zip 
                    List(Coord(1, 1), Coord(2, 27), Coord(1, 53), Coord(0, 27), Coord(0, 677), Coord(13, 27))
    
    for (test <- testCases) {
      assert(parseAll(cell, test._1).get == test._2, "(" + test._1 + " == " + test._2.toString + ")")
    }
  }
}