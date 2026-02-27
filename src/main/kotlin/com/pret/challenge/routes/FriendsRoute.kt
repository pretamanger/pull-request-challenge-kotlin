package com.pret.challenge.routes

import com.pret.challenge.models.User
import org.http4k.core.Body
import org.http4k.core.HttpHandler
import org.http4k.core.Method
import org.http4k.core.Request
import org.http4k.core.Response
import org.http4k.core.Status
import org.http4k.core.with
import org.http4k.format.Jackson.auto

private val usersBody = Body.auto<List<User>>().toLens()

fun friendsRoute(externalBaseUrl: String, client: HttpHandler): HttpHandler = { req ->
    val userId = req.query("userId")

    if (userId == null) {
        Response(Status.BAD_REQUEST)
    } else {
        val externalResponse = client(
            Request(Method.GET, "$externalBaseUrl/external-friends-service").query("userId", userId)
        )

        if (!externalResponse.status.successful) {
            Response(Status.BAD_GATEWAY)
        } else {
            val friends = usersBody(externalResponse)
            Response(Status.OK).with(usersBody of friends)
        }
    }
}
