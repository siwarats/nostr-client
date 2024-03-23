package cryptography

interface Schnorr {
    fun getPubKey(privateKey: String): String
    fun sha256hash(message: String): String
    fun sign(privateKey: String, hash: String): String
    fun verify(pubKey: String, signature: String, message: String): Boolean
}

expect fun getSchnorr() : Schnorr