package cardano

import io.javalin.http.Handler
import io.javalin.http.bodyAsClass

class CardanoController {

    companion object {
        val encrypt = Handler { ctx ->
            val request = ctx.bodyAsClass<CardanoRequest>()
            ctx.result(Cardano.encrypt(request.source, request.grid))
        }

        val decrypt = Handler { ctx ->
            val request = ctx.bodyAsClass<CardanoRequest>()
            ctx.result(Cardano.decrypt(request.source, request.grid))
        }

    }

}