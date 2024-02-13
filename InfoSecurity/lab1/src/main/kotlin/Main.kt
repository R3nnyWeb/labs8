import io.javalin.Javalin

fun main() {
    val app = Javalin.create(/*config*/)
        .get("/caesar/encrypt", CaesarController.encrypt)
        .get("/caesar/decrypt", CaesarController.encrypt)
        .get("/caesar/decrypt-force", CaesarController.encrypt)
        .start(7070)
}