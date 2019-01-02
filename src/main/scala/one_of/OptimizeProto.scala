// package one_of
//
//import one_of.ProtoF.TMessage
//import qq.droste.{Basis, Trans, scheme}
//
//object OptimizeProto {
//  /**
//    * This optimization includes the protobuf "OneOf" list as fields, so
//    * that it can be interpreted into correct scala code as a member of a case
//    * class.
//    * */
//  def combineFields[T: Basis[ProtoF, ?]]: T => T = scheme.cata(oneOfsAsFieldsTrans.algebra)
//
//  def oneOfsAsFieldsTrans[T](implicit T: Basis[ProtoF, T]): Trans[ProtoF, ProtoF, T] = Trans {
//    case TMessage(name, messageFields, oneOfs) => {
//
//      val oneOfAsField: Seq[ProtoF.Field[T]] = oneOfs.map(
//        oneOf =>
//          ProtoF.Field(
//            oneOf.name, // TODO: This is not relevant
//            T.algebra(oneOf),
//            999 // TODO: this is no longer relevant
//          ))
//
//      TMessage(name, messageFields ++ oneOfAsField, List())
//    }
//    case other => other
//  }
//
//}
