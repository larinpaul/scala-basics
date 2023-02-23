package com.rockthejvm

object ObjectOrientation extends App {

  // java equivalent: public static void main(String[] args)

  // class and instance
  class Animal {
    // define fields
    val age: Int = 0
    // define methods
    def eat() = println("I'm eating")
  }

  val anAnimal = new Animal

  // inheritance
  class Dog(val name: String) extends Animal // constructor definition
  val aDog = new Dog("Smarty")

  // constructor arguments are NOT fields: needs to put a val before the constructor argument
  aDog.name

  // subtype polymorphism
  val aDeclaredAnimal: Animal = new Dog("Growler")
  aDeclaredAnimal.eat() // the most derived mehtod will be called at runtime

  // abstract class
  abstract class WalkingAnimal {
    val hasLegs = true // by default the fields are public, you can restrict adding by protected or private
    def walk(): Unit
  }

  // "interface" = ultimate abstracty type
  trait Carnivore {
    def eat(animal: Animal): Unit
  }

  trait Philosophe {
    def ?!(though: String): Unit // ?! is a valid method name
  }

  // Scala has single-class inheritance, multi-trait "mixing"
  class Crocodile extends Animal with Carnivore with Philosophe { // we can mix in as any traits as we like
    override def eat(animal: Animal): Unit = println("I am eating you, animal!")
    override def ?!(thought: String): Unit = println(s"I was thinking: $thought")
  }


  val aCroc = new Crocodile
  aCroc.eat(aDog)
  aCroc eat aDog // infix notation = object method argument, only available for methods with ONE argument
  aCroc ?! "What if we could fly?"

  // operators in Scala are actually methods
  val basicMath = 1 + 2 // here + is a method belonging to Int type
  val anotherBasicMath = 1.+(2) // equivalent

  // anonymous classes
  val dinosaur = new Carnivore {
    override def eat(animal: Animal): Unit = println("I am a dinosaur so I can eat pretty much anything")
  }

  /*
    // What you tell the compiler:

   class Carnivore_Anonymous_35728 extends Carnivore {
      override def eat(animal: Animal): Unit = println("I am a dinosaur so I can eat pretty much anything")
   }

   val dinosaur = new Carnivore_Anonymous_35728
   */

  // singleton object
  object MySingleton { // the only instance of the MySingleton type
    val mySpecialValue = 53278
    def mySpecialMethod(): Int = 5327
    def apply(x: Int): Int = x + 1
  }

  MySingleton.mySpecialMethod()
  MySingleton.apply(65)
  MySingleton(65) // equivalent to calling the apply method

  object Animal { // companions - companion object, has the same name as a classs or a trait
    // companions can access each other's private fields/methods
    // singleton Animal and instances of Animal are different things
    val canLiveIndefinitely = false
  }

  val animalsCanLiveForever = Animal.canLiveIndefinitely // you access a Singleton object
                                                         // similarly to "static" methods in C++ and Java
  /*
    case classes = lightweight data structures with some boilerplate
    - sensible equals and hash code
    - serialization
    - companion with apply
    - pattern matching
   */
  case class Person(name: String, age: Int)
  // may be constructed without the keyword new
  val bob = Person("Bob", 54) // equivalent to Person.apply("Bob", 54)

  // exceptions
  try {
    // code that can throw
    val x: String = null
    x.length
  } catch { // catch(Exception e) {...}
    case e: Exception => "some faulty error message"
  } finally {
    // execute some code no matter what
  }

  // generics
  abstract class MyList[T] {
    def head: T
    def tail: MyList[T] // You can use this T as method definitions or value definitions inside this class so that when you use it later, the T becomes concrete
  }

  val aList: List[Int] = List(1,2,3) // List.apply(1,2,3)
  val first = aList.head // int
  val rest = aList.tail
  val aStringList = List("hello", "Scala")
  val firstString = aStringList.head // string

  // Point #1: In Scala we usually operate with IMMUTABLE values/objects
  // Any modification to an object must return ANOTHER object
  /*
    Benefits:
    1) Works miracles in multithreaded/distributed env
    2) Helps making sense of the code ("reasoning about")
   */
  val reversedList = aList.reverse // returns a NEW list

  // Point #2: Scala is closest to the Object-Oriented ideal




}
