package derive_traverse

sealed trait Tree
case class Branch(left: Tree, right: Tree) extends Tree
case class Node(id: Either[String, Int], name: String, value: Tree) extends Tree
case class BoxedString(value: String) extends Tree
case class BoxedInt(value: Int) extends Tree


