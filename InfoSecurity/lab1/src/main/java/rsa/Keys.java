package rsa;

import java.math.BigInteger;
import java.util.Base64;

public class Keys {
    public BigInteger publicKey;
    public BigInteger privateKey;
    public BigInteger n;

    public String publicKey64;
    public String privateKey64;
    public String n64;

    public Keys() {
    }

    public Keys(BigInteger publicKey, BigInteger privateKey, BigInteger n) {
        this.publicKey = publicKey;
        this.privateKey = privateKey;
        this.n = n;
        this.n64 = Base64.getEncoder().encodeToString(n.toByteArray());
        this.publicKey64 = Base64.getEncoder().encodeToString(publicKey.toByteArray());
        this.privateKey64 = Base64.getEncoder().encodeToString(privateKey.toByteArray());
    }

    @Override
    public String toString() {
        return "rsa.Keys{" +
                "openKey=" + publicKey +
                "\n, privateKey=" + privateKey +
                "\n, n=" + n +
                '}';
    }
}
