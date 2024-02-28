import cardano.CardanoController
import gamma.GammaController
import io.javalin.Javalin


fun main() {
    val app = Javalin.create { config ->
        config.bundledPlugins.enableCors { cors ->
            cors.addRule {
                it.anyHost()
            }
        }
    }
        .get("/caesar/encrypt", CaesarController.encrypt)
        .get("/caesar/decrypt", CaesarController.decrypt)
        .get("/caesar/decrypt-force", CaesarController.tryDecrypt)
        .post("/cardano/encrypt", CardanoController.encrypt)
        .post("/cardano/decrypt", CardanoController.decrypt)
        .post("/gamma/crypt", GammaController.crypt)
        .post("/gamma/encrypt-hex", GammaController.encryptHex)
        .post("/gamma/decrypt-hex", GammaController.decryptHex)
        .exception(Exception::class.java) { e, ctx ->
            ctx.status(500)
            ctx.result(e.message ?: "Unknown error")
            e.printStackTrace()
        }
        .start(7070)
}