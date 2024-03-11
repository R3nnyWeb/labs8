package vernan;

import java.nio.charset.Charset;
import java.util.Random;

public class Vernan {

    static Charset charset = Charset.forName("Windows-1251");

    public static String generateKey(int size) {
        Random random = new Random();
        byte[] keyBytes = new byte[size];
        random.nextBytes(keyBytes);

        return new String(keyBytes, charset);
    }

    public static String crypt(String input, String key) {

        if (key.length() != input.length()) {
            throw new IllegalArgumentException("Длина ключа не равна длине входящего текста");
        }

        byte[] inputBytes = input.getBytes(charset);
        byte[] keyBytes = key.getBytes(charset);

        byte[] result = new byte[input.length()];

        for (int i = 0; i < keyBytes.length; i++) {
            result[i] = (byte) (inputBytes[i] ^ keyBytes[i]);
        }

        return new String(result, charset);
    }

}
