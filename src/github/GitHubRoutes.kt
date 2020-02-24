package com.suryadigital.automatedturk.github

import io.ktor.application.call
import io.ktor.http.ContentType
import io.ktor.response.respondText
import io.ktor.routing.Route
import io.ktor.routing.post

fun Route.gitHub() {
    post("/webhook") {
        call.respondText("OK", contentType = ContentType.Text.Plain)
    }
}
