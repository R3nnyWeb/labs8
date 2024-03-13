package vizhener;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Vizhener {
    private static final Map<Character, Integer> charToIndex = new HashMap<>();
    private static final Map<Integer, Character> indexToChar = new HashMap<>();
    static String ALPHABET = "абвгдеёжзийклмнопрстуфхцчшщъыьэюя ";

    private static int[][] table;

    static {
        int n = ALPHABET.length();
        for (int i = 0; i < n; i++) {
            charToIndex.put(ALPHABET.charAt(i), i);
            indexToChar.put(i, ALPHABET.charAt(i));
        }

        table = new int[n][n];

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                table[i][j] = (j + i) % n;
            }
        }
    }

    public static String encrypt(String input, String key) {
        if (input.length() != key.length()) throw new IllegalArgumentException("input != key");

        int[] inputIndexes = stringToIndexes(input);
        int[] keyIndexes = stringToIndexes(key);

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < input.length(); i++) {
            int cryptedIndex = table[inputIndexes[i]][keyIndexes[i]];
            sb.append(indexToChar.get(cryptedIndex));
        }

        return sb.toString();
    }

    public static String decrypt(String encrypted, String key) {
        if (encrypted.length() != key.length()) throw new IllegalArgumentException("input != key");

        int[] encryptedIndexes = stringToIndexes(encrypted);
        int[] keyIndexes = stringToIndexes(key);

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < encrypted.length(); i++) {
            int encryptedRowI = keyIndexes[i];
            int sourceIndex = (encryptedIndexes[i] - encryptedRowI + table.length) % table.length;
            sb.append(indexToChar.get(sourceIndex));
        }

        return sb.toString();
    }


    //Как в hill
    private static String indexesToString(List<Integer> resultIndexes) {
        var sb = new StringBuilder();
        for (int i : resultIndexes) {
            sb.append(indexToChar.get(i));
        }
        return sb.toString();
    }

    //Как в hill

    private static int[] stringToIndexes(String input) {
        int[] indexes = new int[input.length()];
        for (int i = 0; i < input.length(); i++) {
            if (!charToIndex.containsKey(input.charAt(i))) throw new IllegalArgumentException("Символ не найден " + input.charAt(i));
            indexes[i] = charToIndex.get(input.charAt(i));
        }
        return indexes;
    }

}
