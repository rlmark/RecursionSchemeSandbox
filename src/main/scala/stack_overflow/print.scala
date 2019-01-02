package stack_overflow

import qq.droste.{Algebra, Basis, scheme}
import stack_overflow.TreeF.{BranchF, NodeF, PrimitiveInt, PrimitiveString, RepeatNodeF, _}

trait Printer[A] {
  def print(a: A): String

  def contramap[B](f: B => A): Printer[B] = Printer(f.andThen(print))
}

object Printer {
  def apply[A](f: A => String): Printer[A] = (a: A) => f(a)

  val evaluateAlgebra: Algebra[TreeF, String] = Algebra {
    case PrimitiveInt() => "Int"
    case PrimitiveString() => "String"
    case NodeF(value) => s"$value"
    case RepeatNodeF(value) => s"List[$value]"
    case BranchF(l, r) => s"Either[$l,$r]"
  }

  def printSchema[T: Basis[TreeF, ?]]: Printer[T] = Printer(scheme.cata(evaluateAlgebra))
}
