package nostr.message

import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.JsonPrimitive
import kotlinx.serialization.json.buildJsonArray
import kotlinx.serialization.json.decodeFromJsonElement
import kotlinx.serialization.json.encodeToJsonElement
import kotlinx.serialization.json.jsonArray
import kotlinx.serialization.json.jsonPrimitive
import nostr.Constant.MESSAGE_TYPE_REQUEST

data class RequestMessage(
    val subscribeId: String,
    val filter: RequestFilter? = null,
) : Message() {

    override val type: String = MESSAGE_TYPE_REQUEST

    override fun toJsonString(): String {
        val arrayElement = buildJsonArray {
            add(JsonPrimitive(type))
            add(JsonPrimitive(subscribeId))
            add(jsonEncoder.encodeToJsonElement(filter))
        }
        return jsonEncoder.encodeToString(arrayElement)
    }

    companion object {

        fun fromJsonString(value: String): RequestMessage? {
            val arrayElement = jsonDecoder.parseToJsonElement(value).jsonArray
            val event = arrayElement[0].jsonPrimitive.content
            return if (event == MESSAGE_TYPE_REQUEST) {
                val subscribeId = arrayElement[1].jsonPrimitive.content
                val filter = arrayElement.getOrNull(2)?.let {
                    jsonDecoder.decodeFromJsonElement<RequestFilter>(it)
                }
                RequestMessage(subscribeId, filter)
            } else {
                null
            }
        }
    }
}