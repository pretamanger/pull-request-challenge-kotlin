package com.pret.challenge.services

import com.pret.challenge.data.friendships
import com.pret.challenge.data.users
import com.pret.challenge.models.User

/**
 * Mock database call for the purposes of the challenge.
 * No need to review this.
 */
fun getFriendsForUser(userId: String): List<User> {
    val friends = friendships.mapNotNull { (userId1, userId2) ->
        val isMatchingFriendship = userId == userId1 || userId == userId2
        if (!isMatchingFriendship) return@mapNotNull null

        val friendId = if (userId == userId1) userId2 else userId1
        users.firstOrNull { it.id == friendId }
    }

    return friends.distinctBy { it.id }
}
