package cryptography

import fr.acinq.secp256k1.Hex
import fr.acinq.secp256k1.Secp256k1
import java.security.MessageDigest


class AndroidSchnorr : Schnorr {
    override fun getPubKey(privateKey: String): String {
        return Secp256k1.pubkeyCreate(Hex.decode(privateKey))
            .let {
                // compressed
                Hex.encode(it, 1, 32)
            }
    }

    override fun sha256hash(message: String): String {
        val md = MessageDigest.getInstance("SHA-256")
        val sha256hash = md.digest(message.toByteArray())
        return Hex.encode(sha256hash)
    }

    override fun sign(privateKey: String, hash: String): String {
        return Secp256k1.signSchnorr(
            Hex.decode(hash),
            Hex.decode(privateKey),
            null
        ).let { Hex.encode(it) }
    }

    override fun verify(pubKey: String, signature: String, message: String): Boolean {
        return Secp256k1.verifySchnorr(
            Hex.decode(signature),
            Hex.decode(message),
            Hex.decode(pubKey)
        )
    }
}

actual fun getSchnorr(): Schnorr = AndroidSchnorr()
