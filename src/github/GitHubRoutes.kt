package com.suryadigital.automatedturk.github

import com.google.gson.Gson
import com.google.gson.JsonObject
import io.ktor.application.call
import io.ktor.http.ContentType
import io.ktor.request.receive
import io.ktor.response.respondText
import io.ktor.routing.Route
import io.ktor.routing.post

fun Route.gitHub() {
    post("/webhook") {
        call.respondText("ok")
    }
}
