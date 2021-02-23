package essentials

import akka.actor.{Actor, ActorSystem, Props}

object Actors extends App {
  val actorSystem = ActorSystem("firstActorSystem")
  println(actorSystem.name)

  class WordCountActor extends Actor {
    var totalWords = 0

    override def receive: PartialFunction[Any, Unit] = {
      case message: String =>
        println(s"Received $message")
        totalWords += message.split(" ").length
      case msg => println(s"[word counter] I cannot understand ${msg.toString}")
    }
  }

  val wordCounter = actorSystem.actorOf(Props[WordCountActor], "wordCounter")

  wordCounter ! "Ahhhhhhh!"

  class Counter extends Actor {
    var count = 0;

    override def receive: Receive = {
      case "Increment" => count += 1
      case "Decrement" => count -= 1
      case "Print" => println(count)
    }
  }

  val counter = actorSystem.actorOf(Props[Counter], "counter")
  counter ! "Increment"
  counter ! "Print"
  counter ! "Decrement"
  counter ! "Print"

  object BankAccount{
    case class Deposit(amount: Int)
    case class Withdraw(amount: Int)
    case object Statement

    case class Success(message: String)
    case class Failure(reason: String)
  }

  class BankAccount extends Actor {
    import BankAccount._
    var balance = 0

    override def receive: Receive = {
      case Deposit(amount) =>
        if (amount < 0) sender() ! Failure("Invalid amount")
        else {
          balance += amount
          sender() ! Success("success")
        }
      case Withdraw(amount) =>
        if (amount < 0 || amount > balance) sender() ! Failure("Invalid")
        else {
          balance -= amount
          sender() ! Success("s")
        }
      case Statement => println(balance)
    }
  }
}
