package vizhener;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class VizhenerTest {

    @Test
    void encrypt_input_length_is_not_equal_to_key_length() {
        String input = "абв";
        String key = "ффффф";

        assertThrows(IllegalArgumentException.class, () -> {
            Vizhener.encrypt(input, key);
        });
    }

    @Test
    void encrypt_wrong_symbol() {
        String input = "абв";
        String key = "123";

        assertThrows(IllegalArgumentException.class, () -> {
            Vizhener.encrypt(input, key);
        });
    }

    @Test
    void encrypt_shifted_last_symbols() {
        String input = "б";
        String key = " ";

        assertEquals("а", Vizhener.encrypt(input, key));
    }

    @Test
    void encrypt() {
        String input = "абвг";
        String key = "эюя ";

        assertEquals("эяав", Vizhener.encrypt(input, key));
    }

        @Test
    void decrypt_input_length_is_not_equal_to_key_length() {
        String input = "абв";
        String key = "ффффф";

        assertThrows(IllegalArgumentException.class, () -> {
            Vizhener.decrypt(input, key);
        });
    }

    @Test
    void decrypt_wrong_symbol() {
        String input = "абв";
        String key = "123";

        assertThrows(IllegalArgumentException.class, () -> {
            Vizhener.decrypt(input, key);
        });
    }

    @Test
    void decrypt() {
        String input = "гдебабае";
        String key = "бвагбваг";
        String encrypted = Vizhener.encrypt(input, key);
        System.out.println(encrypted);

        assertEquals(input, Vizhener.decrypt(encrypted, key));
    }



}