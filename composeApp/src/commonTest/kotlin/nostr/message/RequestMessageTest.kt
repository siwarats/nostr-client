import nostr.message.RequestFilter
import nostr.message.RequestMessage
import kotlin.test.Test
import kotlin.test.assertEquals

class RequestMessageTest {

    @Test
    fun `convert object to json string correctly`() {
        assertEquals(MESSAGE_JSON, MESSAGE_OBJ.toJsonString())
    }

    @Test
    fun `create object from json string correctly`() {
        assertEquals(MESSAGE_OBJ, RequestMessage.fromJsonString(MESSAGE_JSON))
    }

    companion object {
        private const val MESSAGE_JSON = "[\"REQ\",\"my-01\",{\"kinds\":[1,23],\"limit\":5}]"
        private val MESSAGE_OBJ = RequestMessage(
            subscribeId = "my-01",
            filter = RequestFilter(
                kinds = listOf(1, 23),
                limit = 5
            )
        )
    }
}