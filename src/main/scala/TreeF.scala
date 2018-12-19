import cats.implicits._
import cats.{Applicative, Traverse}
import qq.droste.Coalgebra
import qq.droste.util.DefaultTraverse

sealed trait TreeF[A]

object TreeF {

  final case class BranchF[A](left: A, right: A) extends TreeF[A]

  final case class StringNodeF[A](a: String) extends TreeF[A]

  final case class RepeatNodeF[A](valueToRepeat: String) extends TreeF[A]

  implicit val traverseExprF: Traverse[TreeF] =
    new DefaultTraverse[TreeF] {
      def traverse[G[_]: Applicative, A, B](fa: TreeF[A])(f: A => G[B]): G[TreeF[B]] =
        fa match {
          case StringNodeF(s) => (StringNodeF(s): TreeF[B]).pure[G] // DO THIS.
          case RepeatNodeF(r) => (RepeatNodeF(r): TreeF[B]).pure[G]
          case BranchF(l, r) => (f(l), f(r)).mapN(BranchF(_, _))
        }
    }

//  val embedAlgebra: Algebra[TreeF, Tree] = Algebra {
//    case StringNodeF(v) => Node(v, false)
//    case RepeatNodeF(a) => Node(a, true)
//    case BranchF(l, r) => Branch(l, r)
//  }

  val fromTree: Coalgebra[TreeF, Tree] = Coalgebra {
    case Branch(l, r) => BranchF(l, r)
    case n: Node if n.repeats => RepeatNodeF(n.value)
    case n: Node => StringNodeF(n.value)
  }
}


