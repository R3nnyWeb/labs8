package rsa;

import java.math.BigInteger;
import java.util.*;

public class RSA {


    public static GenerateKeysResponse generatePrivate(int bitLength) {
        BigInteger p = generatePrimeNumber(bitLength);

        BigInteger q = generatePrimeNumber(bitLength);

        BigInteger n = p.multiply(q);

        BigInteger euler = (p.subtract(BigInteger.ONE)).multiply(q.subtract(BigInteger.ONE));

        BigInteger publicKey = generatePublicKey(euler);

        BigInteger privateKey = generatePrivateKey(publicKey, euler);

        return new GenerateKeysResponse(publicKey, privateKey, p, q, euler, n);
    }

    public static BigInteger generatePrivateKey(BigInteger publicKey, BigInteger euler) {
        return publicKey.modInverse(euler);
    }

    public static BigInteger generatePublicKey(BigInteger euler) {
        BigInteger publicKey;
        do {
            publicKey = BigInteger.probablePrime(euler.bitLength() - 1, new Random());
        } while (!publicKey.gcd(euler).equals(BigInteger.ONE));
        return publicKey;
    }

    public static BigInteger generatePrimeNumber(int bitLength) {
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
            BigInteger encryptedIndex = BigInteger.valueOf((int) character).modPow(publicKey, n);
            encryptedIndexes.add("Character = " + character + " Char index = " + (int) character + ", Encrypted index = " + encryptedIndex + "!!!"); // Это для защиты, можно не добавлять
            encryptedMessage.append(Base64.getEncoder().encodeToString(encryptedIndex.toByteArray()));
            encryptedMessage.append(" ");
        }
        System.out.println("Encrypted indexes: ");
        System.out.println(encryptedIndexes);
        return encryptedMessage.toString().trim();
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