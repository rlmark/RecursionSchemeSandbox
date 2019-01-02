package one_of

sealed trait BaseDescriptor

/** The top-level class representing a .proto file */
case class FileDescriptor(descriptors: List[Descriptor]) extends BaseDescriptor

/** Represents a Message block
  * message Person {
  *   string name = 1;
  * }
  * */
case class Descriptor(name: String, fields: List[FieldDescriptor], oneOfs: List[OneOf]) extends BaseDescriptor

/** Can be a primitive type or another nested Descriptor but not another OneOf*/
case class FieldDescriptor(name: String, tpe: String, position: Int) extends BaseDescriptor

/** Makes its field list into a coproduct. Only 1 can be present.
  * */
case class OneOf(name: String, fields: List[FieldDescriptor]) extends BaseDescriptor

