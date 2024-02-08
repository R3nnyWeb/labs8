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
            assertThrows(IllegalArgumentException.class, () -> {
                sut.encrypt("abz", -1);
            });
        }

        @Test
        public void more_then_one_word() {
            var result = sut.encrypt("abc cde", 1);

            assertEquals("bcd def", result);
        }
    }

}