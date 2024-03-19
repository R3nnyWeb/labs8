package rsa;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.math.BigInteger;
import java.util.Base64;

@JsonSerialize
@JsonDeserialize
public class GenerateKeysResponse {

    public BigInteger publicKey;
    public BigInteger privateKey;
    public String publicKey64;
    public String privateKey64;
    public BigInteger p;
    public BigInteger q;
    public BigInteger euler;
    public BigInteger n;

    public GenerateKeysResponse() {
    }

    public GenerateKeysResponse(BigInteger publicKey, BigInteger privateKey, BigInteger p, BigInteger q, BigInteger euler, BigInteger n) {
        this.publicKey = publicKey;
        this.privateKey = privateKey;
        this.p = p;
        this.q = q;
        this.euler = euler;
        this.n = n;
        this.publicKey64 = Base64.getEncoder().encodeToString(publicKey.toByteArray());
        this.privateKey64 = Base64.getEncoder().encodeToString(privateKey.toByteArray());
    }


    @Override
    public String toString() {
        return "GenerateKeysResponse{" + "publicKey=" + publicKey + "\n, privateKey=" + privateKey + "\n, p=" + p + "\n, q=" + q + "\n, eiler=" + euler + "\n, n=" + n + "\n, publicKey64='" + publicKey64 + '\'' + "\n, privateKey64='" + privateKey64 + '\'' + '}';
    }
}
