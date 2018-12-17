import qq.droste.{Algebra, Basis, scheme}
import TreeF._
trait Printer[A] {
  def print(a: A): String
  def contramap[B](f: B => A): Printer[B] = Printer(f.andThen(print))
}

object Printer {
  def apply[A](f: A => String): Printer[A] = (a: A) => f(a)

  def printSchema[T: Basis[TreeF, ?]]: Printer[T] = {
    val algebra: Algebra[TreeF, String] = Algebra {
    case StringNodeF() => "String"
    case RepeatNode(l) => s"List[$l]"
    case BranchF(l, r) => s"Either[$l,$r]"
    }

    Printer(scheme.cata(algebra))
  }
}
