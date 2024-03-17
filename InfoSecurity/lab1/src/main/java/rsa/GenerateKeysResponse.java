package rsa;

import java.math.BigInteger;
import java.util.Base64;

public class GenerateKeysResponse {

    BigInteger publicKey;
    BigInteger privateKey;
    String publicKey64;
    String privateKey64;
    BigInteger p;
    BigInteger q;
    BigInteger eiler;
    BigInteger n;


    public GenerateKeysResponse(BigInteger publicKey, BigInteger privateKey, BigInteger p, BigInteger q, BigInteger eiler, BigInteger n) {
        this.publicKey = publicKey;
        this.privateKey = privateKey;
        this.p = p;
        this.q = q;
        this.eiler = eiler;
        this.n = n;
        this.publicKey64 = Base64.getEncoder().encodeToString(publicKey.toByteArray());
        this.privateKey64 = Base64.getEncoder().encodeToString(privateKey.toByteArray());
    }


    @Override
    public String toString() {
        return "GenerateKeysResponse{" +
                "publicKey=" + publicKey +
                "\n, privateKey=" + privateKey +
                "\n, p=" + p +
                "\n, q=" + q +
                "\n, eiler=" + eiler +
                "\n, n=" + n +
                "\n, publicKey64='" + publicKey64 + '\'' +
                "\n, privateKey64='" + privateKey64 + '\'' +
                '}';
    }
}
