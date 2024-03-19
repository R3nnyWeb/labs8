package rsa;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.util.*;

public class RSA {


    public static GenerateKeysResponse generate(int bitLength) {
        BigInteger p = generatePrimeNumber(bitLength);

        BigInteger q = generatePrimeNumber(bitLength);

        BigInteger n = p.multiply(q);

        BigInteger euler = (p.subtract(BigInteger.ONE)).multiply(q.subtract(BigInteger.ONE));

        BigInteger publicKey = generatePublicKey(euler);

        BigInteger privateKey = generatePrivateKey(publicKey, euler);

        GenerateKeysResponse response = new GenerateKeysResponse(publicKey, privateKey, p, q, euler, n);

        System.out.println(response);

        return response;
    }

    private static BigInteger generatePrivateKey(BigInteger publicKey, BigInteger euler) {
        return publicKey.modInverse(euler);
    }

    private static BigInteger generatePublicKey(BigInteger euler) {
        BigInteger publicKey;
        do {
            publicKey = BigInteger.probablePrime(euler.bitLength() - 1, new Random());
        } while (!publicKey.gcd(euler).equals(BigInteger.ONE));
        return publicKey;
    }

    private static BigInteger generatePrimeNumber(int bitLength) {
        Random rnd = new Random();
        BigInteger prime = BigInteger.probablePrime(bitLength, rnd);
        while (!prime.isProbablePrime(100)) {
            prime = BigInteger.probablePrime(bitLength, rnd);
        }
        return prime;
    }

    public static String encrypt(String message, BigInteger publicKey, BigInteger n) {
        StringBuilder encryptedMessage = new StringBuilder();
        List<String> encryptedIndexes = new ArrayList<>();
        for (int i = 0; i < message.length(); i++) {
            char character = message.charAt(i);
            BigInteger encryptedIndex = BigInteger.valueOf(character).modPow(publicKey, n);
            encryptedIndexes.add("Character = " + character + " Char index = " + (int) character + ", Encrypted index = " + encryptedIndex + "!!!"); // Это для защиты, можно не добавлять
            encryptedMessage.append(Base64.getEncoder().encodeToString(encryptedIndex.toByteArray()));
            encryptedMessage.append(" ");
        }
        System.out.println("Encrypted indexes: ");
        System.out.println(encryptedIndexes);
        return encryptedMessage.toString().trim();
    }

    //не надо
    public static String encrypt2(String message, BigInteger publicKey, BigInteger n) {
// Получаем длину модуля n в байтах
        int nByteLength = n.toByteArray().length;

        // Переводим сообщение в числовое представление
        byte[] bytes = message.getBytes();

        // Разбиваем сообщение на блоки, длиной nByteLength - 1 байт
        int blockSize = nByteLength - 1;
        StringBuilder encryptedMessage = new StringBuilder();
        for (int i = 0; i < bytes.length; i += blockSize) {
            int endIndex = Math.min(i + blockSize, bytes.length);
            byte[] block = new byte[endIndex - i];
            System.arraycopy(bytes, i, block, 0, block.length);
            BigInteger m = new BigInteger(block);
            // Вычисляем шифротекст c = m^e mod n
            BigInteger c = m.modPow(publicKey, n);


            // Преобразуем каждый блок шифротекста в строку Base64 и добавляем к зашифрованному сообщению
            byte[] encryptedBlockBytes = c.toByteArray();
            String encryptedBlockBase64 = Base64.getEncoder().encodeToString(encryptedBlockBytes);
            encryptedMessage.append(encryptedBlockBase64).append(" ");
        }

        // Возвращаем зашифрованное сообщение в виде строки
        return encryptedMessage.toString().trim();
    }

    public static String decrypt2(String encryptedMessage, BigInteger privateKey, BigInteger n) {
        // Разбиваем зашифрованное сообщение на блоки
        String[] encryptedBlocks = encryptedMessage.split(" ");

        // Декодируем каждый блок из Base64 и расшифровываем его
        StringBuilder decryptedMessage = new StringBuilder();
        for (String encryptedBlock : encryptedBlocks) {
            // Декодируем блок из Base64
            byte[] encryptedBlockBytes = Base64.getDecoder().decode(encryptedBlock);

            // Преобразуем его в BigInteger
            BigInteger c = new BigInteger(1, encryptedBlockBytes);

            // Расшифровываем блок c = c^d mod n
            BigInteger m = c.modPow(privateKey, n);

            // Преобразуем расшифрованный блок в массив байт и добавляем к расшифрованному сообщению
            byte[] decryptedBlockBytes = m.toByteArray();
            decryptedMessage.append(new String(decryptedBlockBytes));
        }

        // Возвращаем расшифрованное сообщение в виде строки
        return decryptedMessage.toString();
    }

    public static String decrypt(String encryptedMessage, BigInteger privateKey, BigInteger n) {
        StringBuilder decryptedMessage = new StringBuilder();
        String[] encryptedCharacters = encryptedMessage.split(" ");
        for (String encryptedCharacter : encryptedCharacters) {
            byte[] encryptedBytes = Base64.getDecoder().decode(encryptedCharacter);
            BigInteger encryptedIndex = new BigInteger(encryptedBytes);
            BigInteger decryptedIndex = encryptedIndex.modPow(privateKey, n);
            decryptedMessage.append((char) decryptedIndex.intValue());
        }
        return decryptedMessage.toString();
    }

}