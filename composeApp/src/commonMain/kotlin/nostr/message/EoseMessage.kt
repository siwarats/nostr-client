package nostr.message

import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.JsonPrimitive
import kotlinx.serialization.json.buildJsonArray
import kotlinx.serialization.json.jsonArray
import kotlinx.serialization.json.jsonPrimitive
import nostr.Constant.MESSAGE_TYPE_EOSE

data class EoseMessage(
    val subscribeId: String
) : Message() {

    override val type: String = MESSAGE_TYPE_EOSE

    override fun toJsonString(): String {
        val arrayElement = buildJsonArray {
            add(JsonPrimitive(type))
            add(JsonPrimitive(subscribeId))
        }
        return jsonEncoder.encodeToString(arrayElement)
    }

    companion object {

        fun fromJsonString(value: String): EoseMessage? {
            val arrayElement = jsonDecoder.parseToJsonElement(value).jsonArray
            val event = arrayElement[0].jsonPrimitive.content
            return if (event == MESSAGE_TYPE_EOSE) {
                val subscribeId = arrayElement[1].jsonPrimitive.content
                EoseMessage(subscribeId)
            } else {
                null
            }
        }
    }
}