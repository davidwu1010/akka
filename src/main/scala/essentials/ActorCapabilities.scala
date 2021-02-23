package essentials

import akka.actor.{Actor, ActorSystem, Props}

object ActorCapabilities extends App {

  class SimpleActor extends Actor {
    override def receive: Receive = {
      case message: String => println(s"Received $message")
      case number: Int =>
        println(this)
        println(self)
    }
  }

  val system = ActorSystem("actorSystem")
  val simpleActor = system.actorOf(Props[SimpleActor], "simpleActor")

  simpleActor ! 2
}
