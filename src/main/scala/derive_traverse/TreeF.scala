package derive_traverse

import qq.droste.Coalgebra
import qq.droste.macros.deriveTraverse


@deriveTraverse sealed trait TreeF[A]

@deriveTraverse sealed trait FieldF[A]

object TreeF {
  @deriveTraverse final case class NamedField[A](fieldName: String, tpe: A) extends FieldF[A]
  @deriveTraverse final case class NumberedField[A](fieldNumber: Int, tpe: A) extends FieldF[A]

  final case class BranchF[A](left: A, right: A) extends TreeF[A]
  final case class NodeF[A](name: String, a: FieldF[A]) extends TreeF[A]
  final case class IntF[A](age: Int) extends TreeF[A]
  final case class StringF[A](age: String) extends TreeF[A]

  val fromTree: Coalgebra[TreeF, Tree] = Coalgebra {
    case Branch(l, r) => BranchF(l, r)
    case BoxedInt(v) => IntF(v)
    case BoxedString(v) => StringF(v)
    case n@Node(Right(int), name, v) => NodeF(name, NumberedField(int, v))
    case n@Node(Left(str), name, v ) => NodeF(name, NamedField(str, v))
  }
}
