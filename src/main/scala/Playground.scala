import qq.droste.data.Mu
import qq.droste.data.Mu._
import qq.droste.scheme

object Playground extends App {
  val testTree = Branch(
    Branch(
      Node("Hello", false),
      Node("specialNode", true)
    ),
    Node("HI", false))

  val parseOriginalTree: Tree => Mu[TreeF] = scheme.ana(TreeF.fromTree)

  val printTree: Mu[TreeF] => String = Printer.printSchema.print _

  val res = printTree(parseOriginalTree(testTree))

  println(res)
}
