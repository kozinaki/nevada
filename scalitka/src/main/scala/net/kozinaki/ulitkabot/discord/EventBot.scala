package net.kozinaki.ulitkabot.discord

import net.dv8tion.jda.api.entities.{Message, MessageChannel}
import net.dv8tion.jda.api.events.message.MessageReceivedEvent
import net.dv8tion.jda.api.hooks.ListenerAdapter
import net.kozinaki.ulitkabot.docker.Docker
import net.kozinaki.ulitkabot.exception.ParseCommandException
//import net.kozinaki.nevada.discordbot.exception.ParseCommandException;
//import net.kozinaki.nevada.docker.DockerAPI;

import java.util.{Collections, List}

import Command._

import scala.jdk.CollectionConverters._


class EventBot extends ListenerAdapter {

    var dockerAPI: Docker = new Docker();

    override def onMessageReceived(event: MessageReceivedEvent) {
        var msg: Message = event.getMessage();
        if (msg.getContentRaw().equals("!ping")) {
            var channel: MessageChannel = event.getChannel();
            var time: Long = System.currentTimeMillis();
            channel.sendMessage("Pong!") /* => RestAction<Message> */
                    .queue(response /* => Message */ => {
                        response.editMessageFormat("Pong: %d ms", System.currentTimeMillis() - time).queue();
                    });
        }

        var data: Data = parse(msg.getContentRaw());
        data.getCommand() match {
            case LIST => sendList(event)
             case STOP => stop(data.getData);
             case START => start(data.getData);
            case UNKNOWN => ;
        }
    }

    def parse(content: String): Data = {
        try {
            return new Data(content);
        } catch {
            case e: ParseCommandException => println(e.getMessage());
        }
        return new Data;
    }

    def sendList(event: MessageReceivedEvent) {
        var channel: MessageChannel = event.getChannel();
        channel.sendMessage(getList()).queue();
    }

    def getList(): String = {
        var containersName: List[String] = getContainers();
        Collections.sort(containersName);
        return containersName.stream.reduce((one: String, two: String) => one.concat(";\n").concat(two)).get();
    }

    def stop(name: String): Unit = {
        dockerAPI.stopContainer(name);
    }
    def start(name: String): Unit = {
        dockerAPI.startContainer(name);
    }

    def getContainers(): List[String] = {
        return dockerAPI.getContainersName().asJava;
    }
}
