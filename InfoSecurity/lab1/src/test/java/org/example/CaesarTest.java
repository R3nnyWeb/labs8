package org.example;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CaesarTest {
    private static final char start = 'a';
    private static final char end = 'z';
    private final Caesar sut = new Caesar(start, end);

    @Nested
    class EncryptTest {

        @Test
        public void encrypt_first_letter_by_one() {
            var result = sut.encrypt("a", 1);

            assertEquals("b", result);
        }

        @Test
        public void encrypt_last_letter_by_one() {
            var result = sut.encrypt("z", 1);

            assertEquals("a", result);
        }

        @Test
        public void encrypt_letter_with_shift() {
            var result = sut.encrypt("a", 3);

            assertEquals("d", result);
        }

        @Test
        public void encrypt_letter_with_max_shift() {
            var result = sut.encrypt("a", end - start + 1);

            assertEquals("a", result);
        }

        @Test
        public void shift_word() {
            var result = sut.encrypt("abz", 2);

            assertEquals("cdb", result);
        }

        @Test
        public void letter_less_then_alphabet() {
            assertThrows(IllegalArgumentException.class, () -> {
                sut.encrypt(String.valueOf('a' - 10), 1);
            });
        }

        @Test
        public void letter_more_then_alphabet() {
            assertThrows(IllegalArgumentException.class, () -> {
                sut.encrypt(String.valueOf('z' + 10), 1);
            });
        }

        @Test
        public void zero_shift() {
            var result = sut.encrypt("abz", 0);

            assertEquals("abz", result);
        }

        @Test
        public void negative_shift() {
            var result = sut.encrypt("zab", -2);

            assertEquals("xyz", result);
        }

        @Test
        public void more_then_one_word() {
            var result = sut.encrypt("abc cde", 1);

            assertEquals("bcd def", result);
        }
    }

    @Nested
    class DecryptTest {

        @Test
        public void decrypt_from_encrypt() {
            var source = "some source words zz";

            var encrypted = sut.encrypt(source, 3);

            var decrypted = sut.decrypt(encrypted, 3);

            assertEquals(decrypted, source);
        }

        @Test
        public void decrypt_as_encrtypt_with_reversed_shift() {
            var source = "some source words zz";

            var encrypted = sut.encrypt(source, 3);

            var decrypted = sut.encrypt(encrypted, -3);

            assertEquals(decrypted, source);
        }

        @Test
        public void decrypt_by_force_with_min_and_max() {
            var source = "some source words zz";
            var encrypted = sut.encrypt(source, 5);

            List<String> result = sut.tryDecrypt(encrypted, 1, 6);

            assertEquals(6, result.size());
            System.out.println(result);
            assertTrue(result.contains(source));
        }

        @Test
        public void generic_decrypt_by_force() {
            var source = "some source words zz";
            var encrypted = sut.encrypt(source, 5);

            List<String> result = sut.tryDecrypt(encrypted);

            assertEquals('z' - 'a' + 1, result.size());
            System.out.println(result);
            assertTrue(result.contains(source));
        }

    }

}