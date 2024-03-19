import cardano.CardanoController
import gamma.GammaController
import hill.HillController
import io.javalin.Javalin
import rsa.RSA
import rsa.RSAController
import vernam.VernamController
import vizhener.VizhenerController


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
        .post("/hill/encrypt", HillController.encrypt)
        .post("/hill/decrypt", HillController.decrypt)
        .post("/vizhener/encrypt", VizhenerController.encrypt)
        .post("/vizhener/decrypt", VizhenerController.decrypt)
        .post("/vernam/crypt", VernamController.encrypt)
        .get("/vernam/generate", VernamController.generate)
        .get("/rsa/generate", RSAController.generate)
        .post("/rsa/encrypt", RSAController.encrypt)
        .post("/rsa/decrypt", RSAController.decrypt)
        .exception(Exception::class.java) { e, ctx ->
            ctx.status(500)
            ctx.result(e.message ?: "Unknown error")
            e.printStackTrace()
        }
        .start(7070)
}