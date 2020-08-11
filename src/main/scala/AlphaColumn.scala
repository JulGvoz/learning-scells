package scells
package alphacolumn {

  case class AlphaStringWrapper(str: String) {
    def toColumn: Int = {
      val length = str.length
      // get powers like AAB -> 625, 25, 1
      val powers = (length until 0 by -1) map ((x: Int) => math.pow(26, x - 1).toInt) 
      val values = for ((char, power) <- str zip powers) yield {
        (char - 'A' + 1)*power
      }
      values.sum
    }
  }

  case class AlphaIntWrapper(col: Int) {
    def toAlpha: String = {
      def decompose(v: Int): List[Int] = v match {
        case x if x <= 26 => x :: Nil // its just a single letter, output just it
        case y if y % 26 == 0 => {
          26 :: decompose((y - 1) / 26) // special case: Z. since it is mod 26 == 0, it messes up things
        }
        case z => z % 26 :: decompose(z / 26) // regular case. just cascade down, like normal decimal/binary/base-n decomposition
      }
      (decompose(col) map ((x: Int) => (x + 'A' - 1).toChar))
      .mkString.reverse
    }
  }

}

import scala.language.implicitConversions
import alphacolumn._

package object alphacolumn {
  implicit def wrapStringAlpha(str: String): AlphaStringWrapper = 
    AlphaStringWrapper(str)
  
  implicit def wrapIntAlpha(col: Int): AlphaIntWrapper = 
    AlphaIntWrapper(col)
}