package hill;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class HillTest {

    @Test
    void encrypt_key_determinant_zero() {
        double[][] key = {{0, 0}, {0, 0}};

        var e = assertThrows(IllegalArgumentException.class, () -> {
            Hill.encrypt(key, "");
        }).getMessage();
        assertEquals("Для данной матрицы модульной обратной матрицы не существует", e);
    }

    @Test
    void encrypt_key_not_square() {
        double[][] key = {{1, 2, 3}, {3, 4, 5}};

        var e = assertThrows(IllegalArgumentException.class, () -> {
            Hill.encrypt(key, "");
        }).getMessage();
        assertEquals("Матрица не квадратная", e);
    }

    @Test
    void cant_find_modulo_inverse() {
        var e = assertThrows(IllegalArgumentException.class, () -> {
            Hill.modInverse(2, 10);
        }).getMessage();
        assertEquals("Для данной матрицы модульной обратной матрицы не существует", e);
    }

    @Test
    void find_modulo_inverse() {
        assertEquals(6, Hill.modInverse(-2, 11));
    }

    @Test
    void encrypt() {
        double[][] key = {{3, 7}, {21, 3}};
        assertEquals("шяё?х!жк", Hill.encrypt(key, "кусикич"));
    }

    @Test
    void decrypt() {
        double[][] key = {{3, 7}, {21, 3}};
        var source = "кусикич";
        var encrypted = Hill.encrypt(key, source);
        assertEquals(source + " ", Hill.decrypt(key, encrypted));
    }



}