import io.javalin.http.Context
import io.javalin.http.Handler


class CaesarController(
) {

    companion object {
        val encrypt = Handler { ctx ->
            val caesar = Caesar(ctx.queryParam("start")!!.first(), ctx.queryParam("end")!!.first())
            ctx.result(caesar.encrypt(ctx.queryParam("source"), ctx.queryParam("k")!!.toInt()))
        }

        val decrypt = Handler { ctx ->
            val caesar = Caesar(ctx.queryParam("start")!!.first(), ctx.queryParam("end")!!.first())
            ctx.result(caesar.decrypt(ctx.queryParam("source"), ctx.queryParam("k")!!.toInt()))
        }

        val tryDecrypt = Handler { ctx ->
            val caesar = Caesar(ctx.queryParam("start")!!.first(), ctx.queryParam("end")!!.first())
            ctx.json(caesar.tryDecrypt(ctx.queryParam("source")))
        }

    }
}