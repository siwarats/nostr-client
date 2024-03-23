package nostr.message

import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.JsonPrimitive
import kotlinx.serialization.json.buildJsonArray
import kotlinx.serialization.json.decodeFromJsonElement
import kotlinx.serialization.json.encodeToJsonElement
import kotlinx.serialization.json.jsonArray
import kotlinx.serialization.json.jsonPrimitive
import nostr.Constant.MESSAGE_TYPE_EVENT

data class EventMessage(
    val subscribeId: String,
    val eventContent: EventContent,
) : Message() {

    override val type: String = MESSAGE_TYPE_EVENT

    override fun toJsonString(): String {
        val arrayElement = buildJsonArray {
            add(JsonPrimitive(type))
            add(JsonPrimitive(subscribeId))
            add(jsonEncoder.encodeToJsonElement(eventContent))
        }
        return jsonEncoder.encodeToString(arrayElement)
    }

    companion object {

        fun fromJsonString(value: String): EventMessage? {
            val arrayElement = jsonDecoder.parseToJsonElement(value).jsonArray
            val event = arrayElement[0].jsonPrimitive.content
            return if (event == MESSAGE_TYPE_EVENT) {
                val subscribeId = arrayElement[1].jsonPrimitive.content
                val eventContent = arrayElement.getOrNull(2)?.let {
                    jsonDecoder.decodeFromJsonElement<EventContent>(it)
                }
                if (eventContent != null) {
                    EventMessage(subscribeId, eventContent)
                } else {
                    null
                }
            } else {
                null
            }
        }
    }
}