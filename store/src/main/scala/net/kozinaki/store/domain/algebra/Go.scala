package net.kozinaki.store.domain.algebra

object Go {

  def main(args: Array[String]): Unit = {
    var rat: Rational = new Rational(1, 2);
    println(rat * 2)
    println(2 * rat)
  }

}
