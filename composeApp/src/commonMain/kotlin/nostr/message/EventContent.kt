package nostr.message

import cryptography.Schnorr
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.JsonPrimitive
import kotlinx.serialization.json.buildJsonArray
import kotlinx.serialization.json.encodeToJsonElement
import nostr.Constant.EVENT_KIND_TEXT_NOTE

@Serializable
data class EventContent(
    @SerialName("id") val id: String,
    @SerialName("kind") val kind: Int,
    @SerialName("pubkey") val pubKey: String,
    @SerialName("sig") val sig: String,
    @SerialName("content") val content: String,
    @SerialName("created_at") val createdAt: Long,
    @SerialName("tags") val tags: List<List<String>>? = null,
) {
    fun generateId(schnorr: Schnorr): String {
//        val tagsJson = tags
//            ?.map { tag ->
//                val tagItemJson = tag.map { tagItem ->
//                    JsonPrimitive(tagItem)
//                }
//                return@map JsonArray(tagItemJson)
//            }
//            ?: emptyList()
        Message.jsonEncoder.encodeToString(tags)
        val arrayElement = buildJsonArray {
            add(JsonPrimitive(0))
            add(JsonPrimitive(pubKey))
            add(JsonPrimitive(createdAt))
//            add(JsonArray(tagsJson))
            add(JsonPrimitive(EVENT_KIND_TEXT_NOTE))
            add(Message.jsonEncoder.encodeToJsonElement(tags))
            add(JsonPrimitive(content))
        }
        val generatedId = Message.jsonEncoder.encodeToString(arrayElement)
        return schnorr.sha256hash(generatedId)
    }
}