package net.kozinaki.ulitkabot

import net.dv8tion.jda.api.JDABuilder
import net.dv8tion.jda.api.entities.Activity
import net.dv8tion.jda.api.utils.Compression
import net.dv8tion.jda.api.utils.cache.CacheFlag
import net.kozinaki.ulitkabot.discord.{Action, EventBot}
import net.kozinaki.ulitkabot.config.Prop._;

object Helix {

  def main(args: Array[String]): Unit = {

    val builder = JDABuilder.createDefault(getDiscordToken())

    // Disable parts of the cache
    builder.disableCache(CacheFlag.ACTIVITY, CacheFlag.VOICE_STATE)
    // Enable the bulk delete event
    builder.setBulkDeleteSplittingEnabled(false)
    // Disable compression (not recommended)
    builder.setCompression(Compression.NONE)
    // Set activity (like "playing Something")
    builder.setActivity(Activity.listening(Action.MUSIC))

    builder.addEventListeners(new EventBot())

    val jda = builder.build
    new Thread(() => {
      def foo() = {
        while (!Thread.currentThread.isInterrupted) {
          sleep(20000L)
          val random = (Math.random * 3 + 1).toInt
          random match {
            case 1 =>
              jda.getPresence.setActivity(Activity.listening(Action.MUSIC))
            case 2 =>
              jda.getPresence.setActivity(Activity.watching(Action.MOVIE))
            case 3 =>
              jda.getPresence.setActivity(Activity.playing(Action.GAME))
          }
        }
      }
      foo()
    }).start()
  }

  private def sleep(time: Long): Unit = {
    try Thread.sleep(time)
    catch {
      case e: InterruptedException =>
        Thread.currentThread.interrupt()
    }
  }

}
