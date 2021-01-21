package net.kozinaki.kt.ktor

import io.ktor.application.*
import io.ktor.features.*
import io.ktor.http.*
import io.ktor.response.*
import io.ktor.routing.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*

fun main(args: Array<String>) {
    embeddedServer(Netty, 8080, watchPaths = listOf("AppKt"), module = Application::module).start()
}

fun Application.module() {
    install(DefaultHeaders)
    install(CallLogging)
    install(Routing) {
        get("/") {
            call.respondText("Lets hello world begin!", ContentType.Text.Html)
        }
        get("/rabbit") {
            call.respondText("Secret word sent!", ContentType.Text.Html)
            sayHello(call.request.queryParameters["secret"]);
        }
    }
}

