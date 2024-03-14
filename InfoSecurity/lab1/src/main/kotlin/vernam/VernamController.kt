package vernam

import io.javalin.http.Handler
import io.javalin.http.bodyAsClass
import vernan.Vernan

class VernamController {


    companion object {
        val encrypt = Handler { ctx ->
            val request = ctx.bodyAsClass<VernamRequest>()
            ctx.result(Vernan.crypt(request.input, request.key))
        }

        val generate = Handler { ctx ->
            val size = ctx.queryParam("size")!!.toInt()
            ctx.result(Vernan.generateKey(size))
        }
    }
}