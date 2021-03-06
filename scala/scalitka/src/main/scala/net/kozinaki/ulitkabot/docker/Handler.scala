package net.kozinaki.ulitkabot.docker

import java.util.concurrent.{BlockingQueue, LinkedBlockingQueue, TimeUnit}

import scala.concurrent.{Await, Future}
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration.Duration

object Handler {

  val TAG: String = "[Handler] ";
  val tasks = java.util.Collections.newSetFromMap(new java.util.concurrent.ConcurrentHashMap[String, java.lang.Boolean])
  var queue: BlockingQueue[_] = new LinkedBlockingQueue();

  def execute(id: String, data: String, func: Function[Unit, Unit]): Unit = {
    println(TAG + "task " + id + " " + data + " already executing: " + tasks.contains(data));
    if (!tasks.contains(data)) {
      println("add task: " + id + " " + data)
      tasks.add(data);
      Future {
        func.apply()
        println("remove task: " + id + " " + data)
        tasks.remove(data)
      }
    }
  }

  def main(args: Array[String]): Unit = {
    val greetingFuture = Future {
      Thread.sleep(4000)
      println("calculating...");
      "Hello"
    }
    val greeting = Await.result(greetingFuture, Duration.apply(5, TimeUnit.SECONDS));
  }

}
