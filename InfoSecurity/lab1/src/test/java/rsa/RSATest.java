package rsa;

import org.junit.jupiter.api.Test;
import polig_hellman.PoligHellman;

import static org.junit.jupiter.api.Assertions.*;

class RSATest {

    @Test
    public void encrypt_and_decrypt() {
        for (int i = 0; i < 1000; i++) {


            var keys = RSA.generate(128);

            var text = "текст Baeldung secret message";
            var result1 = RSA.encrypt(text, keys.publicKey, keys.n);
            var result2 = RSA.decrypt(result1, keys.privateKey, keys.n);
            assertEquals(text, result2);
            System.out.println(result1);
        }
    }

    @Test
    public void en2() {
        for (int i = 0; i <1000 ; i++) {
            var keys = RSA.generate(128);


            var text = "текст what about eng? ееекст";

            var result3 = RSA.encrypt2(text, keys.publicKey, keys.n);
            var result4 = RSA.decrypt2(result3, keys.privateKey, keys.n);

            assertEquals(result4, text);
        }


    }

    @Test
    public void encrypt_and_decrypt2() {
        for (int i = 0; i < 10000; i++) {
            var keys = PoligHellman.generateKeys(4);
            var text = "текст Baeldung secret message";
            var result1 = PoligHellman.encrypt(text, keys.publicKey, keys.n);
            var result2 = PoligHellman.decrypt(result1, keys.privateKey, keys.n);
            assertEquals(text, result2);
            System.out.println(result1);
        }
    }


}