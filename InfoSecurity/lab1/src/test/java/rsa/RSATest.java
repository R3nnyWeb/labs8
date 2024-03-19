package rsa;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RSATest {

    @Test
    public void encrypt_and_decrypt() {
        var keys = RSA.generate(512);
        System.out.println(keys);
        var text = "Baeldung secret message";
        var result1 = RSA.encrypt(text, keys.publicKey, keys.n);
        System.out.println(result1);
        var result2 = RSA.decrypt(result1, keys.privateKey, keys.n);
        System.out.println(result2);
        assertEquals(text, result2);
    }

}