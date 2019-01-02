//package stack_overflow
//
//import cats.implicits._
//import cats.{Applicative, Traverse}
//import qq.droste.Coalgebra
//import qq.droste.util.DefaultTraverse
//
//sealed trait TreeF[A]
//
//object TreeF {
//
//  final case class BranchF[A](left: A, right: A) extends TreeF[A]
//
//  final case class NodeF[A](a: A) extends TreeF[A]
//  final case class RepeatNodeF[A](valueToRepeat: A) extends TreeF[A]
//
//  final case class PrimitiveString[A]() extends TreeF[A]
//  final case class PrimitiveInt[A]() extends TreeF[A]
//
//  implicit val traverseExprF: Traverse[TreeF] =
//    new DefaultTraverse[TreeF] {
//      def traverse[G[_] : Applicative, A, B](fa: TreeF[A])(f: A => G[B]): G[TreeF[B]] =
//        fa match {
//          case PrimitiveInt() => (PrimitiveInt[B](): TreeF[B]).pure[G]
//          case PrimitiveString() => (PrimitiveString[B](): TreeF[B]).pure[G]
//          case RepeatNodeF(a) => f(a).map(b => RepeatNodeF(b): TreeF[B])
//          case NodeF(a) => f(a).map(b => NodeF(b): TreeF[B])
//          case BranchF(l, r) => (f(l), f(r)).mapN(BranchF(_, _))
//        }
//    }
//
//  // Either this stack overflows, or if you change the order, you have never see the List type repeated
//  val fromTree: Coalgebra[TreeF, Tree] = Coalgebra {
//    case Branch(l, r) => BranchF(l, r)
//    case n: Node if n.repeats => RepeatNodeF(n)
//    case n: Node => NodeF(n)
//    case Node(Right(_), _ ) => PrimitiveInt()
//    case Node(Left(_), _ ) => PrimitiveString()
//  }
//}
//
//
