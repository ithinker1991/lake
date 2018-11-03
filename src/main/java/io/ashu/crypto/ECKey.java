package io.ashu.crypto;

import io.ashu.core.model.AbstractTransaction;
import java.math.BigInteger;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.Provider;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import org.ethereum.crypto.jce.ECKeyPairGenerator;
import org.ethereum.crypto.jce.SpongyCastleProvider;
import org.spongycastle.asn1.sec.SECNamedCurves;
import org.spongycastle.asn1.x9.X9ECParameters;
import org.spongycastle.crypto.params.ECDomainParameters;
import org.spongycastle.jcajce.provider.asymmetric.ec.BCECPublicKey;
import org.spongycastle.jce.spec.ECParameterSpec;
import org.spongycastle.jce.spec.ECPrivateKeySpec;
import org.spongycastle.math.ec.ECPoint;

public class ECKey {

  public static final String ALGORITHM = "EC";
  public static final ECDomainParameters CURVE;
  public static final ECParameterSpec CURVE_SPEC;
  public static final BigInteger HALF_CURVE_ORDER;
  private static final SecureRandom secureRandom;

  private final PrivateKey privateKey;
  protected final ECPoint pub;
  private final Provider provider;
  private byte[] address;


  static {
    // All clients must agree on the curve to use by agreement. Ethereum uses secp256k1.
    X9ECParameters params = SECNamedCurves.getByName("secp256k1");
    CURVE = new ECDomainParameters(params.getCurve(), params.getG(), params.getN(), params.getH());
    CURVE_SPEC = new ECParameterSpec(params.getCurve(), params.getG(), params.getN(), params.getH());
    HALF_CURVE_ORDER = params.getN().shiftRight(1);
    secureRandom = new SecureRandom();
  }


  public static ECKey newECKey() throws Exception {
    return new ECKey();
  }

  public static ECKey fromPrivKey(byte[] privateKey) throws Exception {
    return new ECKey(privateKey);
  }

  public static ECKey fromPublicOnly(byte[] pub) {
    return new ECKey(null, CURVE.getCurve().decodePoint(pub));
  }

  private ECKey(byte[] privateKeyBytes) throws NoSuchAlgorithmException, InvalidKeySpecException {
    this.provider = SpongyCastleProvider.getInstance();
    BigInteger privateKey = new BigInteger(1, privateKeyBytes);
    this.pub = CURVE.getG().multiply(privateKey);
    this.privateKey = KeyFactory.getInstance(ALGORITHM, SpongyCastleProvider.getInstance()).
        generatePrivate(new ECPrivateKeySpec(privateKey, CURVE_SPEC));

  }

  private ECKey(PrivateKey privateKey, ECPoint pub) {
    this.provider = SpongyCastleProvider.getInstance();
    this.privateKey = privateKey;
    this.pub = pub;
  }

  private ECKey() throws Exception {
    this(secureRandom);
  }

  private ECKey(SecureRandom secureRandom) {
    this(SpongyCastleProvider.getInstance(), secureRandom);
  }

  private ECKey(Provider provider, SecureRandom secureRandom) {
    this.provider = provider;

    final KeyPairGenerator keyPairGen = ECKeyPairGenerator.getInstance(provider, secureRandom);
    final KeyPair keyPair = keyPairGen.generateKeyPair();

    this.privateKey = keyPair.getPrivate();

    final PublicKey pubKey = keyPair.getPublic();
    if (pubKey instanceof BCECPublicKey) {
      pub = ((BCECPublicKey) pubKey).getQ();
    } else {
      pub = null;
    }

  }

  public AbstractTransaction sign(AbstractTransaction transaction) {
    byte[] hash = HashUtil.sha3(transaction.serialize());
    return transaction;
  }

  public byte[] sign(byte[] data) {
    byte[] hash = HashUtil.sha3(data);
    return hash;
  }

  public byte[] getAddress() {
    if (address == null) {
      address = computeAddress(this.pub);
    }
    return address;
  }

  private byte[] computeAddress(ECPoint pub) {
    return HashUtil.sha3omit12(pub.getEncoded(false));
  }
}
