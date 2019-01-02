# RecursionSchemeSandbox

Stripping away all the Protobuf specific details, this project demonstrates the essence of the problem that I'm running into. 

## What this project tries to do: 

In the spirit of Skeuomorph, we take an invariant `Tree` of strings, and read it as a `TreeF` that can work with Droste's recursion scheme library. We then try to `Print` the Tree. 

Tree ==(Coalgebra: parse stuff)==> TreeF ==(Algebra: print stuff)==> String 

Seems simple, however, there is a catch! Our original `Tree` contains important Type information that our TreeF represents in different classes of its ADT, and it also has a boolean flag on its terminal nodes which indicates whether the `Node's` value can be repeated. Our `TreeF` schema has two Node that one of these original `Nodes` can be transformed into: a `StringNodeF(a: A)` or a `RepeatNode(a: A)`. We'd like our RepeatNodes to be printed as a `List[String]` or `List[Int]` to represent this. Otherwise they should simply be a `String` or `Int`.

## To see what happens: 
Run the Playground app, it will either Stack overflow or Print out an string of Eithers, with no repeated nodes represented. Switch the order of the case statement in the `fromTree` coalgebra to see the other behavior. 

## Constraints: 
We can't, to my knowlege, change the structure of the original `Tree`, that would be controlled by Protobuf itself. But we have free reign in other parts.  

Also, the constructors of `Tree` are all private, so we can merely receive a `Tree` to parse, not create one. 
