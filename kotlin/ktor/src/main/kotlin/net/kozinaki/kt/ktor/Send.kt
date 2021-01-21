package net.kozinaki.kt.ktor

import com.rabbitmq.client.Channel
import com.rabbitmq.client.ConnectionFactory

private const val HOST = "kozinaki.net"
private const val QUEUE_NAME = "HelloWorldQueue"

private const val EXCHANGE_NAME = "HelloWorldExchange"

fun sayHello(hello: String?) {
    val secretWord: String = if (hello.isNullOrBlank()) "empty" else hello
    val factory = ConnectionFactory()
    factory.host = HOST
    factory.newConnection()
        .use { connection -> connection.createChannel().use { channel -> request(channel, secretWord) } }
}

fun request(channel: Channel, hello: String) {
    channel.queueDeclare(QUEUE_NAME, false, false, false, null)
    channel.basicPublish("", QUEUE_NAME, null, hello.toByteArray())
    println(" [x] Sent '$hello'")
}

fun routing(word: String?, isPublic: Boolean) {
    val secretWord: String = if (word.isNullOrBlank()) "empty" else word

    val factory = ConnectionFactory()
    factory.host = HOST
    factory.newConnection().use { connection ->
        connection.createChannel().use { channel ->
            when (isPublic) {
                true -> public(channel, secretWord)
                false -> secret(channel, secretWord)
            }
        }
    }
}

fun public(channel: Channel, secretWord: String) {
    channel.exchangeDeclare(EXCHANGE_NAME, "direct");
    channel.basicPublish(EXCHANGE_NAME, "public", null, secretWord.toByteArray())
    println(" [x] Sent '$secretWord'")
}

fun secret(channel: Channel, secretWord: String) {
    channel.exchangeDeclare(EXCHANGE_NAME, "direct");
    channel.basicPublish(EXCHANGE_NAME, "secret", null, secretWord.toByteArray())
    println(" [x] Sent '$secretWord'")
}