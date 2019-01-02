//package one_of
//
//import cats.data.NonEmptyList
//import qq.droste.macros.deriveTraverse
//
//@deriveTraverse
//sealed trait MuF[A]
//object MuF {
//
//  final case class Field[A](name: String, tpe: A)
//
//  final case class TInt[A]() extends MuF[A]
//
//  final case class TLong[A]() extends MuF[A]
//
//  final case class TString[A]() extends MuF[A]
//
//  final case class TNamedType[A](name: String) extends MuF[A]
//
//  final case class TContaining[A](values: List[A]) extends MuF[A]
//
//  final case class TCoproduct[A](invariants: NonEmptyList[A]) extends MuF[A]
//
//  final case class TProduct[A](name: String, fields: List[Field[A]]) extends MuF[A]
//
//}
//
