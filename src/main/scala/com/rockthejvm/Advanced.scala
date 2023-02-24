package com.rockthejvm

import scala.concurrent.Future
import scala.util.{Failure, Success, Try}
import scala.concurrent.ExecutionContext.Implicits.global

object Advanced extends App {

  // lazy evaluation
  // An expression is not evaluated until it's first used
  lazy val aLazyValue = 2
  lazy val lazyValueWithSideEffect = {
    println("I am so very lazy!")
    43
  } // nothing is printed out
  val withoutLazyValueWithSideEffect = {
    println("I am so very lazy!")
    43
  } // I am so very lazy!

  // lazy value is first evaluated when it's first used

  val eagerValue = lazyValueWithSideEffect + 1 // I am so very lazy!

  // useful in infinite collections and some more rare cases

  // "pseudo-collections": Option, Try
  def methodWhichCanReturnNull(): String = "Hello, Scala"
  val anOption = Option(methodWhichCanReturnNull()) // Some("Hello, Scala!")
  // option = a "collection" which contains at most one element: Some(value) or None

  // as you can see, there are no null-checks thanks to Option
  val stringProcessing = anOption match {
    case Some(string) => s"I have obtained a valid string: $string"
    case None => "I obtained nothing"
  }
  // map, flatMap, filter

  def methodWhichCanThrowException(): String = throw new RuntimeException()

  // code that is hard to read...
  try {
    methodWhichCanThrowException()
  } catch {
    case e: Exception => "defend against this evil exception"
  }

  // better code
  val aTry = Try(methodWhichCanThrowException())
  // a try = a "collection" with either a value if the code went well, or an exception if the code we threw one

  val anotherStringProecssing = aTry match {
    case Success(validValue) => s"I have obtained a valid string: $validValue"
    case Failure(ex) => s"I have obtained an exception: $ex"
  }
  // both Try and Option have map, flatMap, filter compositional functions? alongside others

  /**
   * Evaluate something on another thread
   * (asynchronous programming)
   */

  val aFutureWithParentheses = Future({ // Future.apply
    println("Loading...")
    Thread.sleep(1000)
    println("I have computed a value.")
    67
    // Loading...
    //
    // Process finished with exit code 0
  }) // The main thread finished before this future had a chance to evaluate
    // this was because it was evaluated on another threaqd

   Thread.sleep(2000)
  // Now it also has time to evaluate
  // Loading...
  // I have computed a value
  //
  // Process finished with exit code 0

  // In real-life cases, we omit the parentheses because this block will be passed as an argument to the Future.apply method
  val aFuture = Future {
    println("Loading...")
    Thread.sleep(1000)
    println("I have computed a value.")
    67
  }

  // Future is a "collection" which contains a value when it's evaluated
  // Future is composable with map, flatMap and filter

  // in very theoreitcal terms, the Future, Try and Option types
  // are called "monads" in functional programming
  // They are very-very abstract and hard to explain
  // For the same of simplicity, we can imagine them as some sort of "collections",
  // but they are actually monads!

  /**
   * Implicits basics
   */
  // #1: implicit arguments
  def aMethodWithImplicitArgs(implicit arg: Int) = arg + 1
  implicit val myImplicitInt = 46
  println(aMethodWithImplicitArgs) // 47
  // we can print it out without passing any arguments
  // aMethodWithImplicitArgs(myImplicitInt)

  // #2: implicilit conversions
  implicit class MyRichInteger(n: Int) {
    def isEven() = n % 2 == 0
  }

  println(23.isEven()) // without the "implicit" keyword, isEven would've turned red
  // the compiler does new MyRichInteger(23).isEven()
  // this makes Scala an incredibly expressive language, but it is also dangerous!
  // do it carefully!

}
