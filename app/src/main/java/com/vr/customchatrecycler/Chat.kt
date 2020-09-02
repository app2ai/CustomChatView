package com.vr.customchatrecycler

data class Chat(
    val textMsg: String,
    val dateOfMessage: String
){
    override fun equals(other: Any?): Boolean {
        try {
            other as Chat
            return this.dateOfMessage == other.dateOfMessage
        }catch (cce: ClassCastException){
            println("Class Cast Exc: ${cce.message}")
            return false
        }
    }

    override fun hashCode(): Int {
        var result = textMsg.hashCode()
        result = 31 * result + dateOfMessage.hashCode()
        return result
    }
}