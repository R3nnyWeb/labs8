package vernan;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class VernanTest {

    @Test
    void double_crypt() {
        String input = "Какой-то текст";
        String key = Vernan.generateKey(input.length());
        String encrypted = Vernan.crypt(input, key);
        System.out.println(encrypted);
        assertEquals(input, Vernan.crypt(encrypted, key));
    }

    @Test
    void generate_key() {
        int i = 0;
        while (i++ < 10) {
            String key = Vernan.generateKey(10);
            assertEquals(10, key.length());
            System.out.println(key);
        }
    }

}