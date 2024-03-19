package rsa

data class RSAGenerateKeysResponse(
    val publicKey64: String,
    val privateKey64: String,
    val n: String
)
