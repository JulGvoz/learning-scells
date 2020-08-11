import org.scalatest._
import flatspec._
import matchers.should._
import org.scalactic.Bool

class AlphaColumnSpec extends AnyFlatSpec with Matchers {
  import scells.alphacolumn._

  def runTests[A, B](tests: Iterable[A], answers: Iterable[B])(f: A => B): Unit = {
    for ((test, ans) <- tests zip answers) {
      assert(f(test) == ans, "(test: " + test + ", correct answer: " + ans + ", given answer: " + f(test) + ")")
    }
  }

  val simpleTestRange = 1 to 26
  val simpleTestAnswers = ('A' to 'Z') map (_.toString)

  val simpleZLess = 1 to 25
  val simpleZLessAns = ('A' to 'Y') map (_.toString)

  val harderTests = List(   27,   28,   52,   53,   54,   78,  677,  678,  702)
  val harderAnswers = List("AA", "AB", "AZ", "BA", "BB", "BZ", "ZA", "ZB", "ZZ")

  val harderZLess = List(27, 28, 53, 54)
  val harderZLessAns = List("AA", "AB", "BA", "BB")

  val hardTests = 19 + 26 * 12 + 26*26 * 7 + 26*26*26 * 10 + 26*26*26*26 * 1 :: // AJGLS
                  26 + 26 * 26 + 26*26 * 26 + 26*26*26 * 26 + 26*26*26*26 * 26 :: // ZZZZZ
                  1 + 26 * 1 + 26*26 * 1 + 26*26*26 * 1 + 26*26*26*26 * 1 :: // AAAAA
                  20 + 26 * 19 + 26*26 * 5 + 26*26*26 * 20 :: // TEST
                  309847980 :: Nil // Generated by toColumn, ZAZZYZ
  val hardAnswers = List("AJGLS", "ZZZZZ", "AAAAA", "TEST", "ZAZZYZ")

  "toAlpha" should "correctly convert ints [1 - 26] to length-1 strings" in {
    runTests(simpleTestRange, simpleTestAnswers)(_.toAlpha)
  }

  it should "convert z-less ints [1 - 25] to length-1 strings" in {
    runTests(simpleZLess, simpleZLessAns)(_.toAlpha)
  }

  it should "convert ints to length-2 strings" in {
    runTests(harderTests, harderAnswers)(_.toAlpha)
  }

  it should "convert z-less ints to length-2 strings" in {
    runTests(harderZLess, harderZLessAns)(_.toAlpha)
  }

  it should "convert hard ints to strings" in {
    runTests(hardTests, hardAnswers)(_.toAlpha)
  }

  "toColumn" should "convert length-1 strings to ints" in {
    runTests(simpleTestAnswers, simpleTestRange)(_.toColumn)
  }

  it should "convert length-2 strings into ints" in {
    runTests(harderAnswers, harderTests)(_.toColumn)
  }

  it should "convert hard strings into ints" in {
    runTests(hardAnswers, hardTests)(_.toColumn)
  }
}