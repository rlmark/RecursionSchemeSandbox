import cats.Functor
import qq.droste.Coalgebra

sealed trait TreeF[A]
object TreeF {
  final case class BranchF[A](left: A, right: A) extends TreeF[A]
  final case class StringNodeF[A]() extends TreeF[A]
  final case class RepeatNode[A](valueToRepeat: A) extends TreeF[A]

  implicit val treeFFunctor: Functor[TreeF] = new Functor[TreeF] {
    override def map[A, B](fa: TreeF[A])(f: A => B): TreeF[B] = fa match {
      case StringNodeF()      => StringNodeF()
      case RepeatNode(value)  => RepeatNode(f(value))
      case BranchF(l, r)      => BranchF(f(l), f(r))
    }
  }

  def fromTree: Coalgebra[TreeF, Tree] = Coalgebra { tree: Tree =>
    tree match {
      case Branch(l,r) => BranchF(l, r)
      // Herein lies the problem. Either stack overflow or unreachable code.
      case _: Node => StringNodeF()
      case n: Node if n.repeats => RepeatNode(n)
    }
  }
}


