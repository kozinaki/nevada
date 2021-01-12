package net.kozinaki.ulitkabot.discord

object Command extends Enumeration {

    type Command = Value
    val LIST = Value("!list")
    val STOP = Value("!stop")
    val START = Value("!start")
    val BUILD = Value("!build")
    val REMOVE = Value("!remove")
    val UNKNOWN = Value("!unknown")

    def getCommand(command: String): Any =  {
        Command.values.find(value => value.toString.equals(command)).getOrElse(UNKNOWN);
    }

}