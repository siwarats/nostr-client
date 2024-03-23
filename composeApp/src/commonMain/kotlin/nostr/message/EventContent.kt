package nostr.message

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class EventContent(
    @SerialName("id") val id: String,
    @SerialName("kind") val kind: Int,
    @SerialName("pubkey") val pubKey: String,
    @SerialName("sig") val sig: String,
    @SerialName("content") val content: String,
    @SerialName("created_at") val createdAt: Long,
//    @SerialName("tags") val tags : List<String>? = null,
)