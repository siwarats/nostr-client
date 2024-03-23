package cryptography

import kotlin.test.Test
import kotlin.test.assertEquals

class SchnorrTest {

    @Test
    fun create_public_key_correct() {
        val pubKey = getSchnorr().getPubKey(
            privateKey = PRIVATE_KEY
        )
        assertEquals(PUBLIC_KEY, pubKey)
    }

    @Test
    fun hash_correct() {
        val sig = getSchnorr().sha256hash(
            message = DATA
        )
        assertEquals(ID, sig)
    }

    @Test
    fun sign_correct() {
        val sig = getSchnorr().sign(
            privateKey = PRIVATE_KEY,
            hash = ID
        )
        assertEquals(SIGNATURE, sig)
    }

    @Test
    fun verify_correct() {
        val isValid = getSchnorr().verify(
            pubKey = PUBLIC_KEY,
            signature = SIGNATURE,
            message = ID
        )
        assertEquals(true, isValid)
    }

    companion object {
        private const val PRIVATE_KEY = "bf1d00ae90b4d60007e8126aa8c610bf7842e8db6054e229c8bc886710d9ad14"
        private const val PUBLIC_KEY = "581948d22f0f0d88741a3d294506089ce4cd7a2699e85072f3107cfe82055a70"
        private const val SIGNATURE = "3a7d8d5c2296f684a77e316d1643c4f04c2a27e8491fd3befa13434a85d2d99980099b0bb12ab1192c995c3147c2644721e8779f21d6759bac5677c121ec44b5"
        private const val ID = "da2a8c3af217984178f1660118de3ebdc09c5ca2a850b825c073b0f9d71db281"
        private const val DATA = "[0,\"581948d22f0f0d88741a3d294506089ce4cd7a2699e85072f3107cfe82055a70\",1710839225,1,[],\"posttttt\"]"
    }
}