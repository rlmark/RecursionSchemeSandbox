import cats.implicits._
import cats.{Applicative, Traverse}
import qq.droste.Coalgebra
import qq.droste.util.DefaultTraverse

import scala.language.higherKinds

sealed trait TreeF[A]

object TreeF {

  final case class BranchF[A](left: A, right: A) extends TreeF[A]
  final case class StringList[A]() extends TreeF[A]
  final case class IntList[A]() extends TreeF[A]
  final case class StringValue[A]() extends TreeF[A]
  final case class IntValue[A]() extends TreeF[A]

  implicit val traverseExprF: Traverse[TreeF] =
    new DefaultTraverse[TreeF] {
      def traverse[G[_] : Applicative, A, B](fa: TreeF[A])(f: A => G[B]): G[TreeF[B]] =
        fa match {
          case IntValue() => (IntValue[B](): TreeF[B]).pure[G]
          case IntList() => (IntList[B](): TreeF[B]).pure[G]
          case StringValue() => (StringValue[B](): TreeF[B]).pure[G]
          case StringList() => (StringList[B](): TreeF[B]).pure[G]
          case BranchF(l, r) => (f(l), f(r)).mapN(BranchF(_, _))
        }
    }

  // Either this stack overflows, or if you change the order, you have never see the List type repeated
  val fromTree: Coalgebra[TreeF, Tree] = Coalgebra {
    case Branch(l, r) => BranchF(l, r)
    case Node(Right(_), true) => IntList()
    case Node(Right(_), false) => IntValue()
    case Node(Left(_), true ) => StringList()
    case Node(Left(_), false ) => StringValue()
  }
}


