package logger

object Logger {
    private val isDebug = true

    fun log(message : String) {
        println(message)
    }
}