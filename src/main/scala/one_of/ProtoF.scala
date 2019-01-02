package one_of

import qq.droste.Coalgebra
import qq.droste.macros.deriveTraverse

/** The ADT that represents a Protobuf file in our own code
  * */
@deriveTraverse
sealed trait ProtoF[A]

object ProtoF {

  final case class TFile[A](messages: List[A])                                                extends ProtoF[A]
  final case class TMessage[A](name: String, fields: List[Field[A]], oneOfs: List[TOneOf[A]]) extends ProtoF[A]
  final case class TInt[A]()                                                                  extends ProtoF[A]
  final case class TString[A]()                                                               extends ProtoF[A]
  final case class TNamedType[A](value: String)                                               extends ProtoF[A]
  @deriveTraverse
  final case class TOneOf[A](name: String, fields: List[Field[A]])                            extends ProtoF[A]
  @deriveTraverse
  final case class Field[A](name: String, tpe: A, position: Int)

  def fromProto: Coalgebra[ProtoF, BaseDescriptor] = Coalgebra {
    case FileDescriptor(d)                => TFile(d)
    case d: Descriptor                    => messageFromDescriptor(d)
    case o: OneOf                         => tOneOfFromDescriptor(o)
    case FieldDescriptor(_, "String", _)  => TString()
    case FieldDescriptor(_, "Int", _)     => TInt()
  }

  def messageFromDescriptor(d: Descriptor): ProtoF[BaseDescriptor] = {
    val fields: List[Field[BaseDescriptor]] = fieldsFromDescriptor(d)

    TMessage[BaseDescriptor](d.name, fields, oneOfFromDescriptor(d))
  }

  def fieldsFromDescriptor(descriptor: Descriptor): List[Field[BaseDescriptor]] = {
      descriptor.fields.map(
        fieldDesc =>
          Field[BaseDescriptor](
            fieldDesc.name,
            fieldDesc,
            fieldDesc.position
          )
      )
  }

  def oneOfFromDescriptor(descriptor: Descriptor): List[TOneOf[BaseDescriptor]] =
    descriptor.oneOfs.map(tOneOfFromDescriptor)

  def tOneOfFromDescriptor(oneOf: OneOf): TOneOf[BaseDescriptor] = {
    val fields = oneOf.fields.map(
      f =>
        Field[BaseDescriptor](
          f.name,
          f,
          f.position
        )
    )
    TOneOf[BaseDescriptor](oneOf.name, fields)
  }

}
