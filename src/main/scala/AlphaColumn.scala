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
    // infinite list of powers: 1, 25, 625, 15625 ...
    val powers = LazyList.from(0) map ((x: Int) => math.pow(26, x).toInt)
    // col divided into its digits  
    val digits = (LazyList.iterate(col)(_ / 26) takeWhile (_ != 0) map (_ % 26)).toList
    /*
    On working of digits:
    Stream.iterate(col)(_ / 26) yields
    637799, 24530, 943, 36, 1, 0, ...
    takeWhile (_ != 0) yields
    637799, 24530, 943, 36, 1
    map (_ % 26) yields
    19, 12, 7, 10, 1
    Thus 58126 describes a 
    */
    val reversed = (digits map {
      (x: Int) => (x + (if (x == 0) 26 else 0) + 'A' - 1).toChar // edge case for Z :/
    }).mkString
    reversed.reverse
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