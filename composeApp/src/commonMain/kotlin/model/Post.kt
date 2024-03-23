package model

data class Post(
    val id: String = "0",
    val pubKey: String,
    val content: String,
    val createdAt: Long
)