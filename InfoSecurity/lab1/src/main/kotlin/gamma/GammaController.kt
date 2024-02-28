package gamma

import cardano.Cardano
import cardano.CardanoRequest
import io.javalin.http.Handler
import io.javalin.http.bodyAsClass

class GammaController {

    companion object {
        val crypt = Handler { ctx ->
            val request = ctx.bodyAsClass<GammaRequest>()
            ctx.result(Gamma.crypt(request.input, request.key, request.lsr))
        }

        val encryptHex = Handler { ctx ->
            val request = ctx.bodyAsClass<GammaRequest>()
            ctx.result(Gamma.encryptToHex(request.input, request.key, request.lsr))
        }

        val decryptHex = Handler { ctx ->
            val request = ctx.bodyAsClass<GammaRequest>()
            ctx.result(Gamma.decryptFromHex(request.input, request.key, request.lsr))
        }

    }

}
