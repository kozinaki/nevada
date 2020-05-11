package net.kozinaki.ulitkabot.config

import java.io.{File, FileNotFoundException, InputStream}
import java.net.URL
import java.util.Properties

import scala.io.BufferedSource


object Prop {

  var dockerSocket: String = null;
  var discordToken: String = null;

  {
    val properties: Properties = new Properties()
    //val source: BufferedSource = scala.io.Source.fromFile("src/main/resources/application.properties")
    val source: BufferedSource = scala.io.Source.fromInputStream(getClass().getClassLoader().getResourceAsStream("application.properties"))

    if (source != null) {
      properties.load(source.bufferedReader())
    } else {
      //logger.error("properties file cannot be loaded at path " + path)
      println("properties file cannot be loaded at path")
      throw new FileNotFoundException("Properties file cannot be loaded");
    }

    dockerSocket = properties.getProperty("docker_socket")
    discordToken = properties.getProperty("discord_token")
  }

  def getDockerSocket(): String = dockerSocket;

  def getDiscordToken(): String = discordToken;

}
