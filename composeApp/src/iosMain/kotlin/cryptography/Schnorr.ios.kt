package cryptography

class IOSSchnorr : Schnorr {
    override fun getPubKey(privateKey: String): String {
        TODO("Not yet implemented")
    }

    override fun sha256hash(message: String): String {
        TODO("Not yet implemented")
    }

    override fun sign(privateKey: String, message: String): String {
        TODO("Not yet implemented")
    }

    override fun verify(pubKey: String, signature: String, message: String): Boolean {
        TODO("Not yet implemented")
    }
}


actual fun getSchnorr(): Schnorr = IOSSchnorr()