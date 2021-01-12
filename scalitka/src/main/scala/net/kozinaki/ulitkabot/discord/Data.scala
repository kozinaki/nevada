package net.kozinaki.ulitkabot.discord

import Command._
import net.kozinaki.ulitkabot.exception.ParseCommandException
;

class Data {

  var command: Any = null
  var data: String = null

  def this(content: String) {
    this()
    if (content != null && !content.isEmpty) {
      val dataz = content.split(" ")
      command = Command.getCommand(dataz(0))
      if (dataz.length > 1) data = dataz(1)
    }
    else throw new ParseCommandException("Cannot parse command!")
  }

  def this(command: Any = UNKNOWN) {
    this()
    this.command = UNKNOWN
  }

  def getCommand(): Any = command

  def getData: String = data

}
