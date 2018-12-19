import qq.droste.{Algebra, Basis, scheme}
import TreeF._

trait Printer[A] {
  def print(a: A): String

  def contramap[B](f: B => A): Printer[B] = Printer(f.andThen(print))
}

object Printer {
  def apply[A](f: A => String): Printer[A] = (a: A) => f(a)

  val evaluateAlgebra: Algebra[TreeF, String] = Algebra {
    case IntValue() => "Int"
    case StringValue() => "String"
    case IntList() => s"List[Int]"
    case StringList() => s"List[String]"
    case BranchF(l, r) => s"Either[$l,$r]"
  }

  def printSchema[T: Basis[TreeF, ?]]: Printer[T] = Printer(scheme.cata(evaluateAlgebra))
}
