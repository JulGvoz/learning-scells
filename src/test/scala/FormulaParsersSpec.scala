import org.scalatest._
import flatspec._
import matchers._

import scells.FormulaParsers._
import scells._

class FormulaParsersSpec extends AnyFlatSpec with should.Matchers {
  "cell" should "be able to parse basic cells" in {
    val testCases = List("A1", "A2", "B1", "A0", "z0", "A13") zip 
                    List(Coord(1, 0), Coord(2, 0), Coord(1, 1), Coord(0, 0), Coord(0, 25), Coord(13, 0))
    
    for (test <- testCases) {
      assert(parseAll(cell, test._1).get == test._2, "(" + test._1 + " == " + test._2.toString + ")")
    }
  }

  it should "parse cells with longer column values" in {
    val testCases = List("A1", "AA2", "BA1", "AA0", "zA0", "AA13") zip 
                    List(Coord(1, 0), Coord(2, 26), Coord(1, 52), Coord(0, 26), Coord(0, 676), Coord(13, 26))
    
    for (test <- testCases) {
      assert(parseAll(cell, test._1).get == test._2, "(" + test._1 + " == " + test._2.toString + ")")
    }
  }

  "FormulaParsers" should "parse an empty string" in {
    parse("") should be (Empty)
  }
}