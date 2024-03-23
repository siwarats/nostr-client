package nostr.message

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RequestFilter(
    @SerialName("ids") val ids: List<String>? = null,
    @SerialName("authors") val authors: List<String>? = null,
    @SerialName("kinds") val kinds: List<Int>,
    @SerialName("#e") val eventIds: List<String>? = null,
    @SerialName("#p") val pubKeys: List<String>? = null,
    @SerialName("#t") val topics: List<String>? = null,
    @SerialName("since") val since: Long? = null,
    @SerialName("until") val until: Long? = null,
    @SerialName("limit") val limit: Int
)