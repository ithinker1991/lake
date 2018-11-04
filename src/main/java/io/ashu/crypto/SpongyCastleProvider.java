package io.ashu.crypto;

import java.security.Provider;
import java.security.Security;
import org.spongycastle.jce.provider.BouncyCastleProvider;

public class SpongyCastleProvider {
  public SpongyCastleProvider() {
  }

  public static Provider getInstance() {
    return Holder.INSTANCE;
  }

  private static class Holder {
    private static final Provider INSTANCE;

    private Holder() {
    }

    static {
      Provider p = Security.getProvider("SC");
      INSTANCE = (Provider)(p != null ? p : new BouncyCastleProvider());
      INSTANCE.put("MessageDigest.ETH-KECCAK-256", "org.ethereum.crypto.cryptohash.Keccak256");
      INSTANCE.put("MessageDigest.ETH-KECCAK-512", "org.ethereum.crypto.cryptohash.Keccak512");
    }
  }

}
