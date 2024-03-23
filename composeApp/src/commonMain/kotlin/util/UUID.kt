package util

/**
 * 32 hexdecimal
 * XXXXXXXX-XXXX-XXXX-XXXX-XXXXXXXXXXXX
 */
fun uuid(): String {
    val hex = listOf("0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "a", "b", "c", "d", "e", "f")
    var randomHex = ""
    for (i in 0..31) {
        if (i == 8 || i == 12 || i == 16 || i == 20) {
            randomHex += "-"
        }
        randomHex += hex.random()
    }
    return randomHex
}