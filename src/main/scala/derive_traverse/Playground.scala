package derive_traverse

import qq.droste.data.{Fix, Mu}
import qq.droste.scheme

object Playground extends App {
  val testTree = Branch(
    Branch(
      Node(Right(1), "Bob", BoxedInt(32)),
      Node(Left("skdfiwer"), "Janet", BoxedInt(43))
    ),
    Node(Left("sdfsdfe"), "Jane", BoxedString("twenty")))

  val parseOriginalTree: Tree => Fix[TreeF2] = scheme.ana(TreeF2.fromTree)

  val printTree: Fix[TreeF2] => String = Printer.printSchema.print _

  val res = printTree(parseOriginalTree(testTree))

  println(res)
}

object BrokenTraverseExample extends App {
  val testTree = Branch(
    Branch(
      Node(Right(1), "Bob", BoxedInt(32)),
      Node(Left("skdfiwer"), "Janet", BoxedInt(43))
    ),
    Node(Left("sdfsdfe"), "Jane", BoxedString("twenty")))

  val parseOriginalTree: Tree => Fix[TreeF] = scheme.ana(TreeF.fromTree)

  val printTree: Fix[TreeF] => String = BrokenPrinter.printSchema.print _

  val res = printTree(parseOriginalTree(testTree))

  println(res)
}


