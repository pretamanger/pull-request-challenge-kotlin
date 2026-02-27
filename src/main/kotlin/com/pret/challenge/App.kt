package com.pret.challenge

import com.pret.challenge.routes.externalFriendsServiceRoute
import com.pret.challenge.routes.friendsRoute
import org.http4k.client.OkHttp
import org.http4k.core.Method.GET
import org.http4k.routing.bind
import org.http4k.routing.routes
import org.http4k.server.Jetty
import org.http4k.server.asServer

fun main() {
    val port = System.getenv("PORT")?.toIntOrNull() ?: 3000
    val externalBaseUrl = System.getenv("EXTERNAL_FRIENDS_SERVICE_BASE_URL") ?: "http://localhost:$port"

    val client = OkHttp()

    val app = routes(
        "/friends" bind GET to friendsRoute(externalBaseUrl, client),
        "/external-friends-service" bind GET to externalFriendsServiceRoute()
    )

    app.asServer(Jetty(port)).start()
    println("Running at http://localhost:$port")
}
