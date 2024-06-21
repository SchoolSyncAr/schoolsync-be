package ar.org.schoolsync.model.utils

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import java.util.*


class JWTDecoder(private val token: String) {
    private var charsetType: Charsets = Charsets.UTF8
    private var claims: MutableMap<String, Any> = mutableMapOf()
    private val mapper = jacksonObjectMapper()

    init {
        require(token.isNotEmpty()) { "Token must not be empty" }
    }

    fun setClaims(newClaims: Set<String>): JWTDecoder {
        newClaims.forEach { claims[it] = "" }
        return this
    }

    fun setCharset(type: Charsets): JWTDecoder {
        charsetType = type
        return this
    }

    fun build(): Map<String, Any> {
        val cleanToken = decode(this.token)
        getClaims(cleanToken)
        return claims
    }

    private fun putAll(token: String) {
        claims.putAll(mapToken(token))
    }

    private fun putSome(token: String) {
        mapToken(token).forEach { (key, value) ->
            if (claims.containsKey(key))
                claims[key] = value
        }
    }

    private fun mapToken(token: String): Map<String, Any> = mapper.readValue(token)

    private fun decode(token: String): String {
        val parts = token.split(".")
        val charset = charset(charsetType.txt)
        return String(Base64.getUrlDecoder().decode(parts[1].toByteArray(charset)), charset)
    }

    private fun getClaims(token: String) {
        if (claims.isEmpty()) putAll(token) else putSome(token)
    }
}

enum class Charsets(val txt: String) {
    UTF8("UTF-8"),
    ISO_8859_1("ISO-8859-1"),
    US_ASCII("US-ASCII"),
    UTF16("UTF-16"),
    UTF16BE("UTF-16BE"),
    UTF16LE("UTF-16LE"),
    UTF32("UTF-32"),
    UTF32BE("UTF-32BE"),
    UTF32LE("UTF-32LE")
}