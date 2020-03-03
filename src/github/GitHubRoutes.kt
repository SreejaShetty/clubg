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
        val payload = call.receive<String>()
        val parsedPayload = Gson().fromJson(payload, JsonObject::class.java)
        val commit = parsedPayload.get("check_suite")
        val commitParse = Gson().fromJson(commit, JsonObject::class.java)
        val headCommit = commitParse.get("head_commit")
        val parseHcommit = Gson().fromJson(headCommit, JsonObject::class.java)
        val commitMessage = parseHcommit.get("message")
        if (commitMessage!=null){
            val ticketPattern= Regex(""""[c][h]\d{2,4}""")
            val ticket:String? = ticketPattern.find(commitMessage.toString())?.value
            print(ticket)
        }
    }
}
