package polig_hellman;

import java.math.BigInteger;
import java.util.Random;
import rsa.Keys;
import rsa.RSA;


public class PoligHellman {


    public static Keys generateKeys(int bits){
        BigInteger n = generatePrimeNumber(bits);

        BigInteger openKey = new BigInteger(bits, new Random());
        while (openKey.compareTo(n.subtract(BigInteger.ONE)) == 1 || !openKey.gcd(n.subtract(BigInteger.ONE)).equals(BigInteger.ONE)) {
            openKey = new BigInteger(bits, new Random());
        }

        BigInteger privateKey = openKey.modInverse(n.subtract(BigInteger.ONE));




        Keys result = new Keys(openKey, privateKey, n);
        System.out.println(result);
        return result;
    }

    public static String encrypt(String message, BigInteger publicKey, BigInteger n) {
        return RSA.encrypt(message, publicKey, n);
    }

    public static String decrypt(String message, BigInteger privateKey, BigInteger n) {
        return RSA.decrypt(message, privateKey, n);
    }

    private static BigInteger generatePrimeNumber(int bitLength) {
        Random rnd = new Random();
        BigInteger prime = BigInteger.probablePrime(bitLength, rnd);
        while (!prime.isProbablePrime(100)) {
            prime = BigInteger.probablePrime(bitLength, rnd);
        }
        return prime;
    }



}
