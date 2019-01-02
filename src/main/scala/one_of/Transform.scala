//package one_of
//
//import cats.data.NonEmptyList
//import qq.droste.Trans
//import one_of.MuF._
//
//class Transform {
//  def transformProto[A]: Trans[ProtoF, MuF, A] = Trans {
//    case ProtoF.TInt()                        => TInt()
//    case ProtoF.TString()                     => TString()
//    case ProtoF.TNamedType(name)              => TNamedType(name)
//    case ProtoF.TMessage(name, fields, _)     => TProduct(name, fields.map(f => Field(f.name, f.tpe)))
//    case ProtoF.TFile(values)                 => TContaining(values)
//    case ProtoF.TOneOf(_, fields)             => TCoproduct(NonEmptyList(fields.head.tpe, fields.tail.map(_.tpe)))
//  }
//}
