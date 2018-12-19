// A tree of strings where the terminal case has a special annotation that we will care about modeling
sealed trait Tree
case class Branch(left: Tree, right: Tree) extends Tree
case class Node(value: Either[String, Int], repeats: Boolean) extends Tree

