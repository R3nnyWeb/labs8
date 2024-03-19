package rsa

import io.javalin.http.Handler
import io.javalin.http.bodyAsClass
import java.math.BigInteger
import java.util.*

class RSAController {

    companion object {

        val generate = Handler { ctx ->
            val size = ctx.queryParam("size")!!.toInt()
            val key = RSA.generate(size)
            val response = RSAGenerateKeysResponse(
                publicKey64 = key.publicKey64,
                privateKey64 = key.privateKey64,
                n = key.n.toString()
            )
            ctx.json(response)
        }

        val encrypt = Handler { ctx ->
            val request = ctx.bodyAsClass<RSARequest>()
            val publicBytes: ByteArray = Base64.getDecoder().decode(request.key)
            val key = BigInteger(publicBytes)
            ctx.result(RSA.encrypt(request.input, key, request.n.toBigInteger()))
        }

        val decrypt = Handler { ctx ->
            val request = ctx.bodyAsClass<RSARequest>()
            val bytes: ByteArray = Base64.getDecoder().decode(request.key)
            val key = BigInteger(bytes)
            ctx.result(RSA.decrypt(request.input, key, request.n.toBigInteger()))
        }

    }
}