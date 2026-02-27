package com.pret.challenge.routes

import com.pret.challenge.models.User
import com.pret.challenge.services.getFriendsForUser
import org.http4k.core.Body
import org.http4k.core.HttpHandler
import org.http4k.core.Response
import org.http4k.core.Status
import org.http4k.core.with
import org.http4k.format.Jackson.auto

private val usersBody = Body.auto<List<User>>().toLens()

fun externalFriendsServiceRoute(): HttpHandler = { req ->
    val userId = req.query("userId")

    if (userId == null) {
        Response(Status.BAD_REQUEST)
    } else {
        val friends = getFriendsForUser(userId)
        Response(Status.OK).with(usersBody of friends)
    }
}
