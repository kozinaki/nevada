package net.kozinaki.kt.ktor

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.DeliverCallback;
import com.rabbitmq.client.Delivery;
import java.nio.charset.Charset

private const val HOST = "kozinaki.net"
private const val QUEUE_NAME = "HelloWorldSecret"

fun main() {
    val factory = ConnectionFactory();
    factory.host = HOST
    val connection: Connection = factory.newConnection();
    val channel: Channel = connection.createChannel();
    channel.queueDeclare(QUEUE_NAME, false, false, false, null);
    println(" [*] Waiting for messages. To exit press CTRL+C");
    val deliverCallback = DeliverCallback { consumerTag: String?, delivery: Delivery ->
        val message = String(delivery.body, Charset.forName("UTF-8"))
        println(" [x] Received secret word '$message'")
    }
    channel.basicConsume(QUEUE_NAME, true, deliverCallback) { consumerTag -> }
}