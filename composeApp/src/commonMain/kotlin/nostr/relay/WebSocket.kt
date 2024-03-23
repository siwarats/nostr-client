package nostr.relay

import kotlinx.coroutines.flow.Flow

interface WebSocket {

    fun response(): Flow<String>

    fun send(message: String)

    fun connect()

    fun disconnect()

    fun connectionState(): ConnectionState

    enum class ConnectionState {
        DISCONNECT,
        CONNECTING,
        CONNECTED
    }

}