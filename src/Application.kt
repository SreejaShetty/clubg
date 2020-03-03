package com.suryadigital.automatedturk

import com.google.gson.Gson
import com.google.gson.JsonElement
import com.google.gson.JsonObject
import com.google.gson.JsonParser
import com.suryadigital.automatedturk.github.gitHub
import io.ktor.application.Application
import io.ktor.application.call
import io.ktor.application.install
import io.ktor.client.HttpClient
import io.ktor.client.features.UserAgent
import io.ktor.client.features.logging.LogLevel
import io.ktor.client.features.logging.Logging
import io.ktor.features.*
import io.ktor.gson.gson
import io.ktor.http.ContentType
import io.ktor.request.receive
import io.ktor.response.respondText
import io.ktor.routing.get
import io.ktor.routing.post
import io.ktor.routing.route
import io.ktor.routing.routing
import io.ktor.server.engine.ShutDownUrl
import org.slf4j.event.Level
import java.text.DateFormat


fun main(args: Array<String>): Unit = io.ktor.server.netty.EngineMain.main(args)

internal val httpClient: HttpClient = HttpClient {
    install(Logging) {
        level = LogLevel.ALL
    }
    install(UserAgent) { agent = "Surya Digital Automated Turk" }
}

@Suppress("unused") // Referenced in application.conf
@kotlin.jvm.JvmOverloads
fun Application.module(@Suppress("UNUSED_PARAMETER") testing: Boolean = false) {
    install(CallLogging) {
        level = Level.INFO
    }

    install(ContentNegotiation){
        gson {
            setPrettyPrinting()
            serializeSpecialFloatingPointValues()
            enableComplexMapKeySerialization()
            setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
        }
    }

    install(DefaultHeaders) {
        header("X-Engine", "Ktor")
    }

    install(ForwardedHeaderSupport)
    install(XForwardedHeaderSupport)

    install(ShutDownUrl.ApplicationCallFeature) {
        shutDownUrl = "/ktor/application/shutdown"
        // A function that will be executed to get the exit code of the process
        exitCodeSupplier = { 0 }
    }

    routing {
        get("/") {
            call.respondText("HEALTHY", contentType = ContentType.Text.Plain)
        }
        route("/github") {
            gitHub()
        }
        post("/"){

            val payload = call.receive<String>()
            val parsedPayload = Gson().fromJson(payload, JsonObject::class.java)
            val commit = parsedPayload.get("check_suite")
            val commitParse = Gson().fromJson(commit, JsonObject::class.java)
            val headCommit = commitParse.get("head_commit")
            val parseHcommit = Gson().fromJson(headCommit, JsonObject::class.java)
            val commitMessage = parseHcommit.get("message")
            call.respondText("ok")
            if (commitMessage!=null){
            val ticketPattern= Regex("""ch\d{2,4}""")
            val ticket:String? = ticketPattern.find(commitMessage.toString())?.value
            print(ticket)
            }
        }
    }
}











