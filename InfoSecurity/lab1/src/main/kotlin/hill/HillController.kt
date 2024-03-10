package hill

import io.javalin.http.Handler
import io.javalin.http.bodyAsClass

class HillController {

    companion object {
        val encrypt = Handler { ctx ->
            val request = ctx.bodyAsClass<HillRequest>()
            ctx.result(Hill.encrypt(request.key, request.input))
        }

        val decrypt = Handler { ctx ->
            val request = ctx.bodyAsClass<HillRequest>()
            ctx.result(Hill.decrypt(request.key, request.input))
        }
    }

}