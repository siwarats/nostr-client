package nostr.relay

import cryptography.Schnorr
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.transform
import nostr.message.CloseMessage
import nostr.message.EoseMessage
import nostr.message.EventMessage
import nostr.message.Message
import nostr.message.RequestMessage

class Relay(
    host: String,
    private val schnorr: Schnorr
) {
    private val webSocket: WebSocket = RelayWebSocket(host)

    init {
        webSocket.connect()
    }

    fun response() = webSocket.response().mapToMessage()

    fun send(message: Message) {
        webSocket.send(message.toJsonString())
    }

    private fun Flow<String>.mapToMessage(): Flow<Message> {
        return this.transform {
            val message = EventMessage.fromJsonString(it)
                ?.takeIf { event ->
                    event.isValidSignature(schnorr)
                }
                ?: EoseMessage.fromJsonString(it)
                ?: CloseMessage.fromJsonString(it)
                ?: RequestMessage.fromJsonString(it)
            if (message != null) {
                emit(message)
            }
        }
    }
}