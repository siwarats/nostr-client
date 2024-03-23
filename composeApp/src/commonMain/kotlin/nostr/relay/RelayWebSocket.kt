package nostr.relay

import io.ktor.client.HttpClient
import io.ktor.client.plugins.websocket.ClientWebSocketSession
import io.ktor.client.plugins.websocket.WebSockets
import io.ktor.client.plugins.websocket.wss
import io.ktor.http.HttpMethod
import io.ktor.websocket.Frame
import io.ktor.websocket.close
import io.ktor.websocket.readText
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class RelayWebSocket(
    private val host: String
) : WebSocket, CoroutineScope {

    private val job = Job()

    private val client = HttpClient { install(WebSockets) }

    private var connectionState: WebSocket.ConnectionState = WebSocket.ConnectionState.DISCONNECT

    private var session: ClientWebSocketSession? = null

    private val connection = flow {
        client.wss(method = HttpMethod.Get, host = host) {
            session = this@wss
            connectionState = WebSocket.ConnectionState.CONNECTED
            while (true) {
                val incomingFrame = incoming.receive() as? Frame.Text ?: continue
                val incomingMessage = incomingFrame.readText()
                emit(incomingMessage)
            }
        }
    }

    private val _response = MutableStateFlow<String?>(null)

    override fun response() = _response.filterNotNull()

    override fun send(message: String) {
        launch {
            val frameMessage = Frame.Text(message)
            session?.send(frameMessage)
        }
    }

    override fun connect() {
        launch {
            if (connectionState == WebSocket.ConnectionState.DISCONNECT) {
                connectionState = WebSocket.ConnectionState.CONNECTING
                connection.collect {
                    _response.value = it
                }
            }
        }
    }

    override fun disconnect() {
        launch {
            session?.close()
            connectionState = WebSocket.ConnectionState.DISCONNECT
        }
    }

    override fun connectionState(): WebSocket.ConnectionState {
        return connectionState
    }

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.IO + job
}