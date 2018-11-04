package io.ashu.crypto;

import static java.util.Arrays.copyOfRange;

import org.spongycastle.jcajce.provider.digest.SHA3;
import org.spongycastle.jce.provider.BouncyCastleProvider;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.Provider;
import java.security.Security;

public class HashUtil {

    public static byte[] sha3(byte[] input) {
        SHA3.DigestSHA3 digestSHA3 = new SHA3.Digest256();
        return digestSHA3.digest(input);
    }

    public static byte[] sha3omit12(byte[] input) {
        byte[] hash = sha3(input);
        return copyOfRange(hash, 12, hash.length);
    }
//    private static final Provider CRYPTO_PROVIDER;
//
//    private static final String HASH_256_ALGORITHM_NAME;
//    private static final String HASH_512_ALGORITHM_NAME;
//
//    static {
//        Security.addProvider(TronCastleProvider.getInstance());
//        CRYPTO_PROVIDER = Security.getProvider("SC");
//        HASH_256_ALGORITHM_NAME = "TRON-KECCAK-256";
//        HASH_512_ALGORITHM_NAME = "TRON-KECCAK-512";
//    }
//
//    public static byte[] sha3(byte[] input) {
//        MessageDigest digest;
//        try {
//            digest = MessageDigest.getInstance(HASH_256_ALGORITHM_NAME,
//                    CRYPTO_PROVIDER);
//            digest.update(input);
//            return digest.digest();
//        } catch (NoSuchAlgorithmException e) {
//            throw new RuntimeException(e);
//        }
//
//    }
//
//
//
//    }
}
