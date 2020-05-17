package net.kozinaki.ulitkabot.discord

import net.dv8tion.jda.api.entities.{Message, MessageChannel}
import net.dv8tion.jda.api.events.message.MessageReceivedEvent
import net.dv8tion.jda.api.hooks.ListenerAdapter
import net.kozinaki.ulitkabot.docker.{Docker, Handler}
import net.kozinaki.ulitkabot.exception.{AnotherExecutionException, ParseCommandException}
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
            case LIST => list(event)
            case STOP => stop(data, event);
            case START => start(data, event);
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

    def list(event: MessageReceivedEvent) {
        Handler.execute("!list", "", _ => {
            var channel: MessageChannel = event.getChannel();
            channel.sendMessage(getList()).queue();
        });
    }

    def getList(): String = {
        var containersName: List[String] = getContainers();
        Collections.sort(containersName);
        return containersName.stream.reduce((one: String, two: String) => one.concat(";\n").concat(two)).get();
    }

    def stop(data: Data, event: MessageReceivedEvent): Unit = {
        if (data.getData == null) {
            return
        }
        Handler.execute(data.getCommand().toString, data.getData, _ => {
            try dockerAPI.stopContainer(data.getData)
            catch {
                case e: AnotherExecutionException => {
                    var channel: MessageChannel = event.getChannel();
                    channel.sendMessage(e.message).queue();
                }
            }
        })
    }
    def start(data: Data, event: MessageReceivedEvent): Unit = {
        if (data.getData == null) {
            return
        }
        Handler.execute(data.getCommand().toString, data.getData, _ => {
            try dockerAPI.startContainer(data.getData)
            catch {
                case e: AnotherExecutionException => {
                    var channel: MessageChannel = event.getChannel();
                    channel.sendMessage(e.message).queue();
                }
            }
        });
    }

    def getContainers(): List[String] = {
        return dockerAPI.getContainersName().asJava;
    }
}
