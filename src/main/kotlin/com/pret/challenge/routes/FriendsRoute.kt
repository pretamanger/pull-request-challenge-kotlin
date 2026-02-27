package com.pret.challenge.routes

import com.pret.challenge.models.FriendsTreeNode
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
private val friendsTreeBody = Body.auto<List<FriendsTreeNode>>().toLens()

fun friendsRoute(externalBaseUrl: String, client: HttpHandler): HttpHandler = { req ->
    val userId = req.query("userId")

    if (userId == null) {
        Response(Status.BAD_REQUEST)
    } else {
        val initialFriendsResponse = client(
            Request(Method.GET, "$externalBaseUrl/external-friends-service").query("userId", userId)
        )

        if (!initialFriendsResponse.status.successful) {
            Response(Status.BAD_GATEWAY)
        } else {
            val visitedFriends = mutableSetOf(userId)

            fun getFriendsRecursively(listOfFriends: List<User>): List<FriendsTreeNode> {
                val friendsTree = mutableListOf<FriendsTreeNode>()

                for (friend in listOfFriends) {
                    if (visitedFriends.contains(friend.id)) {
                        continue
                    }
                    visitedFriends.add(friend.id)

                    val externalFriendsResponse = client(
                        Request(Method.GET, "$externalBaseUrl/external-friends-service").query("userId", friend.id)
                    )

                    val friendsOfFriend = if (externalFriendsResponse.status.successful) {
                        usersBody(externalFriendsResponse)
                    } else {
                        emptyList()
                    }

                    val friendNode = FriendsTreeNode(
                        id = friend.id,
                        name = friend.name,
                        friends = getFriendsRecursively(friendsOfFriend)
                    )

                    friendsTree.add(friendNode)
                }

                return friendsTree
            }

            val initialFriends = usersBody(initialFriendsResponse)
            val friendsTree = getFriendsRecursively(initialFriends)
            Response(Status.OK).with(friendsTreeBody of friendsTree)
        }
    }
}
