package rsa

data class RSARequest(
    val input: String,
    val key: String,
    val n: String
)