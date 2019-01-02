package derive_traverse

import qq.droste.{Algebra, Basis, scheme}

trait Printer[A] {
  import derive_traverse.TreeF2._
  def print(a: A): String

  def contramap[B](f: B => A): Printer[B] = Printer(f.andThen(print))
}

object Printer {
  def apply[A](f: A => String): Printer[A] = (a: A) => f(a)

  val evaluateAlgebra: Algebra[TreeF2, String] = Algebra {
    case TreeF2.IntF(v) => s"$v"
    case TreeF2.StringF(v) => s"$v"
    case TreeF2.NodeF(name, TreeF2.NamedField(n, tpe)) => s"$name id:$n age $tpe"
    case TreeF2.NodeF(name, TreeF2.NumberedField(n, tpe)) => s"$name id:$n age ${tpe}"
    case TreeF2.BranchF(l, r) => s"Branch [$l, $r]"
  }

  def printSchema[T: Basis[TreeF2, ?]]: Printer[T] = Printer(scheme.cata(evaluateAlgebra))
}
//
object BrokenPrinter {
  import derive_traverse.TreeF._

  def apply[A](f: A => String): Printer[A] = (a: A) => f(a)

  val evaluateAlgebra: Algebra[TreeF, String] = Algebra {
    case IntF(v) => s"$v"
    case StringF(v) => s"$v"
    case NodeF(name, NamedField(n, tpe)) => s"$name id:$n age $tpe"
    case NodeF(name, NumberedField(n, tpe)) => s"$name id:$n age ${tpe}"
    case BranchF(l, r) => s"Branch [$l, $r]"
  }

  def printSchema[T: Basis[TreeF, ?]]: Printer[T] = Printer(scheme.cata(evaluateAlgebra))

}
