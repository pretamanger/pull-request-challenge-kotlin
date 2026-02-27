package com.pret.challenge.models

data class FriendsTreeNode(
    val id: String,
    val name: String,
    val friends: List<FriendsTreeNode>
)
