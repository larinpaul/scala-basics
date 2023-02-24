package com.rockthejvm

// This only works in Scala 3
object ContextualAbstraction {

  /*
    1 - context parameters/arguments
   */
  val aList = List(2,1,3,4)
  val anOrderedList = aList.sorted // contextual argument: (ordering or descendingOrdering)   // List(1, 2, 3, 4)

  // Ordering (a "glorified" two-argument function)
  given descendingOrdering: Ordering[Int] = Ordering.fromLessThan(_ > _) // (a,b) => a > b   // List(4, 3, 2, 1)
  // a given instance will be automatically fetched by the compiler for every single method that
  // takes a so-called "magical" phantom argument of type Ordering[Int]
  // in this case, for aList.sorted, the compiler will not fetch the given ordering of Int,
  // which is the natural ordering of integers built-in from Scala standard libary,
  // but it will rather use this given instance that I've defined
  // so this will actually be injected with the descending ordering that I've defined here in this scope.

  // given instance is analogous to an implicit val
  // implicits are going to be deprecated in Scala 3

  // In Scala 3, you have the freedom to choose whether your methods will be able to support this behaviour

  trait Combinator[A] { // technically, it is called a monoid
    def combine(x: A, y: A): A
  }

  // we can add an context argument to the combineAll method by using a keyword called "using"
  def combineAll[A](list: List[A])(using combinator: Combinator[A]): A = ???
    list.reduce((a,b) => combinator.combine(a,b))

  given intCombinator: Combinator[Int] = new Combinator[Int] {
    override def combine(x: Int, y: Int) = x + y
  }
  val theSum = combineAll(aList) // (intCombinator)

  /*
    Given places (where the compiler is looking for given instances)
    - local scope
    - imported scope (import yourpackage.given)
    - the companions of all the types involved in the method call (the companion Objects, the companion Singleton instances)
      - companion of List
      - the companion of Int
   */

  // context bounds
  def combineAll_v2[A](list: List[A])(using Combinator[A]): A = ???
  def combineAll_v3[A : Combinator](list: List[A]): A = ???
  // A: Combinator is a form of type restriction

  /*
    where context args are useful
    - type classes (a massive use case!)
    - dependency injection
    - context-dependent functionality
    - type-level programming (a capability in which the Compiler can generate types and prove the relationships between types at compile time)
   */

  /*
    2 - extension methods
   */

  // you can add additional methods to a type after it was defined even if you don't have control over the source of that type
  // and extension methods are heavily used in functional programming libraries such as or and cats effect

  case class Person(name: String) {
    def greet(): String = s"Hi, my name is $name, I love Scala!"
  }

  extension (string: String)
    def greet(): String = new Person(string).greet()

  val danielsGreeting = "Daniel".greet() // as if .greet() was part of the string "Daniel"
  // "type enrichement" = pimping in Scala 2

  // you can also add generic extension methods

  // POWER
  extension [A] (list: List[A])
    def combineAllValues(using combinator: Conbinaor[A]): A =
      list.reduce(combinator.combine)

  val theSum_v2 = aList.combineAllValues


  def main(args: Array[String]): Unit = {
    println(anOrderedList) // List(4,3,2,1)
    println(theSum) // 10
    println(theSum_v2) // 10
  }




}
