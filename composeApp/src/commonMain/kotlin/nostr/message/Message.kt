package nostr.message

import kotlinx.serialization.json.Json

abstract class Message {
    abstract val type: String
    abstract fun toJsonString(): String

    companion object {
        val jsonEncoder = Json
        val jsonDecoder = Json {
            ignoreUnknownKeys = true
        }
    }
}