package com.suryadigital.automatedturk.github

import com.suryadigital.automatedturk.module
import io.ktor.http.HttpMethod
import io.ktor.http.HttpStatusCode
import io.ktor.server.testing.handleRequest
import io.ktor.server.testing.withTestApplication
import kotlin.test.Test
import kotlin.test.assertEquals

class GitHubWebhookTest {
    @Test
    fun testGithubWebHook() {
        withTestApplication({ module(testing = true) }) {
            handleRequest(HttpMethod.Post, "/github/webhook").apply {
                assertEquals(HttpStatusCode.OK, response.status())
                assertEquals("OK", response.content)
            }
        }
    }
}
