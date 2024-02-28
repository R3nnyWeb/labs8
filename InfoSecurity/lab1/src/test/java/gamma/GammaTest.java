package gamma;

import org.junit.jupiter.api.Test;

import java.nio.charset.Charset;

import static gamma.Gamma.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class GammaTest {

    @Test
    void crypt_with_lsr_size_not_equals_key() {
        String input = "a";
        boolean[] key = new boolean[]{true, false, true};
        boolean[] lsr = new boolean[]{true, false, true, false};

        assertThrows(IllegalArgumentException.class, () -> crypt(input, key, lsr));
    }

    @Test
    void crypt_success() {
        String input = "a";
        boolean[] key = new boolean[]{false, false, true, true, false, true, true, true};
        boolean[] lsr = new boolean[]{false, false, false, false, false, true, true, true};
        String result = crypt(input, key, lsr);

        assertEquals(new String(new byte[]{(byte) 0b10001101}, Charset.forName("Windows-1251")), result);
    }

    @Test
    void double_crypt() {
        String input = "a";
        boolean[] key = new boolean[]{false, false, true, true, false, true, true, true};
        boolean[] lsr = new boolean[]{false, false, false, false, false, true, true, true};
        String result1 = crypt(input, key, lsr);
        String result2 = crypt(result1, key, lsr);

        assertEquals(input, result2);
    }

    @Test
    void double_crypt_with_lot_of_text() {
        String input = "3+2=+152151sfas;`   spaces";
        boolean[] key = new boolean[]{false, false, true, true, false, true, true, true};
        boolean[] lsr = new boolean[]{false, false, false, false, false, true, true, true};
        String result1 = crypt(input, key, lsr);
        System.out.println(result1);
        String result2 = crypt(result1, key, lsr);

        assertEquals(input, result2);
    }

    @Test
    void encrypt_and_decrypt_to_hex() {
        String input = "3+2=+152151sfas;`   spaces";
        boolean[] key = new boolean[]{false, false, true, true, false, true, true, true};
        boolean[] lsr = new boolean[]{false, false, false, false, false, true, true, true};
        String result1 = encryptToHex(input, key, lsr);
        System.out.println(result1);
        String result2 = decryptFromHex(result1, key, lsr);

        assertEquals(input, result2);
    }

}
