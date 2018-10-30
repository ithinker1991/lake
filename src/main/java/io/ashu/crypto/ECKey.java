package io.ashu.crypto;

import io.ashu.core.model.AbstractTransaction;
import java.security.InvalidAlgorithmParameterException;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.spec.ECGenParameterSpec;

public class ECKey {
  public PrivateKey privateKey;
  public PublicKey publicKey;

  public static ECKey generateECKey() throws Exception {
    return new ECKey();
  }

  public static ECKey parseFromPrivKey(byte[] privateKey) throws Exception {
    return new ECKey();
  }

  private ECKey() throws Exception {
    init();
  }

  private void init()
      throws Exception {
    KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("ECDSA", "BC");
    SecureRandom secureRandom = SecureRandom.getInstance("SHA1PRNG");
    ECGenParameterSpec ecSpec = new ECGenParameterSpec("prime192v1");
    keyPairGenerator.initialize(ecSpec, secureRandom);
    KeyPair keyPair = keyPairGenerator.generateKeyPair();
    privateKey = keyPair.getPrivate();
    publicKey = keyPair.getPublic();
  }

  public AbstractTransaction sign(AbstractTransaction transaction) {

    byte[] hash = HashUtil.sha3(transaction.serialize());


    return transaction;
  }

  public byte[] sign(byte[] data) {
    byte[] hash = HashUtil.sha3(data);
    return hash;
  }
}
