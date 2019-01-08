package derive_traverse

import cats.{Applicative, Traverse}
import qq.droste.Coalgebra
import cats.implicits._
import cats.{Applicative, Traverse}
import qq.droste.util.DefaultTraverse


sealed trait TreeF2[A]

sealed trait FieldF2[A] {
  val tpe: A
}

object TreeF2 {
  final case class NamedField[A](fieldName: String, tpe: A) extends FieldF2[A]
  final case class NumberedField[A](fieldNumber: Int, tpe: A) extends FieldF2[A]

  final case class BranchF[A](left: A, right: A) extends TreeF2[A]
  final case class NodeF[A](name: String, a: FieldF2[A]) extends TreeF2[A]
  final case class SpecialNodeF[A](specialName: String, a: NamedField[A]) extends TreeF2[A]
  final case class IntF[A](age: Int) extends TreeF2[A]
  final case class StringF[A](age: String) extends TreeF2[A]

  val fromTree: Coalgebra[TreeF2, Tree] = Coalgebra {
    case Branch(l, r) => BranchF(l, r)
    case BoxedInt(v) => IntF(v)
    case BoxedString(v) => StringF(v)
    case Node(Left(str), name, v ) if name == "SpecialName" => SpecialNodeF(name, NamedField(str, v))
    case Node(Right(int), name, v) => NodeF(name, NumberedField(int, v))
    case Node(Left(str), name, v ) => NodeF(name, NamedField(str, v))
  }

  implicit val traverseExprF: Traverse[TreeF2] =
    new DefaultTraverse[TreeF2] {
      def traverse[G[_] : Applicative, A, B](fa: TreeF2[A])(f: A => G[B]): G[TreeF2[B]] =
        fa match {
          case IntF(v) => (IntF[B](v): TreeF2[B]).pure[G]
          case StringF(v) => (StringF[B](v): TreeF2[B]).pure[G]
          case NodeF(a, NamedField(n ,tpe)) => f(tpe).map(t => NodeF(a, NamedField(n, t)): TreeF2[B])
          case SpecialNodeF(a, NamedField(n ,tpe)) => f(tpe).map(t => SpecialNodeF(a, NamedField(n, t)): TreeF2[B])
          case NodeF(a, NumberedField(n ,tpe)) => f(tpe).map(t => NodeF(a, NumberedField(n, t)): TreeF2[B])
          case BranchF(l, r) => (f(l), f(r)).mapN(BranchF(_, _))
        }
    }
}
