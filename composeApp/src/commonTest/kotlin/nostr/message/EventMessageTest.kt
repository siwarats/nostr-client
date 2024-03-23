import nostr.message.EventContent
import nostr.message.EventMessage
import kotlin.test.Test
import kotlin.test.assertEquals

class EventMessageTest {

    @Test
    fun `convert object to json string correctly`() {
        assertEquals(MESSAGE_JSON, MESSAGE_OBJ.toJsonString())
    }

    @Test
    fun `create object from json string correctly`() {
        assertEquals(MESSAGE_OBJ, EventMessage.fromJsonString(MESSAGE_JSON))
    }

    companion object {
        private const val MESSAGE_JSON = "[\"EVENT\",\"my-01\"]"
        private val MESSAGE_OBJ = EventMessage(
            subscribeId = "my-01",
            eventContent = EventContent(
                id = "content-1",
                kind = 1,
                pubKey = "pub-1",
                sig = "sig-1",
                content = "",
                createdAt = 1
            )
        )
    }
}