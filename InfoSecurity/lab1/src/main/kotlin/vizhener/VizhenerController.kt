package vizhener

import io.javalin.http.Handler
import io.javalin.http.bodyAsClass

class VizhenerController {

    companion object {
        val encrypt = Handler { ctx ->
            val request = ctx.bodyAsClass<VizhenerRequest>()
            ctx.result(Vizhener.encrypt(request.input, request.key))
        }

        val decrypt = Handler { ctx ->
            val request = ctx.bodyAsClass<VizhenerRequest>()
            ctx.result(Vizhener.decrypt(request.input, request.key))
        }
    }
}