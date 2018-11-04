package io.ashu.crypto;

import static io.ashu.crypto.BigIntgerUtil.isLessThan;
import static io.ashu.crypto.ByteUtil.bigIntegerToBytes;

import io.ashu.core.model.Transaction;
import java.io.IOException;
import java.math.BigInteger;
import java.nio.charset.Charset;
import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.Provider;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.Signature;
import java.security.SignatureException;
import java.security.spec.InvalidKeySpecException;
import org.spongycastle.asn1.ASN1InputStream;
import org.spongycastle.asn1.ASN1Integer;
import org.spongycastle.asn1.DLSequence;
import org.spongycastle.asn1.sec.SECNamedCurves;
import org.spongycastle.asn1.x9.X9ECParameters;
import org.spongycastle.crypto.digests.SHA256Digest;
import org.spongycastle.crypto.params.ECDomainParameters;
import org.spongycastle.crypto.params.ECPrivateKeyParameters;
import org.spongycastle.crypto.signers.ECDSASigner;
import org.spongycastle.crypto.signers.HMacDSAKCalculator;
import org.spongycastle.jcajce.provider.asymmetric.ec.BCECPrivateKey;
import org.spongycastle.jcajce.provider.asymmetric.ec.BCECPublicKey;
import org.spongycastle.jce.spec.ECParameterSpec;
import org.spongycastle.jce.spec.ECPrivateKeySpec;
import org.spongycastle.math.ec.ECPoint;
import org.spongycastle.util.encoders.Base64;
import org.spongycastle.util.encoders.Hex;

public class ECKey {

  private static final BigInteger SECP256K1N = new BigInteger("fffffffffffffffffffffffffffffffebaaedce6af48a03bbfd25e8cd0364141", 16);

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

  public static ECKey fromprivateKey(byte[] privateKey) throws Exception {
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

  public Transaction sign(Transaction transaction) {
    byte[] hash = HashUtil.sha3(transaction.serialize());
    return transaction;
  }


  public ECDSASignature doSign(byte[] input) {
    if (input.length != 32) {
      throw new IllegalArgumentException("Expected 32 byte input to ECDSA signature, not " + input.length);
    }
    // No decryption of private key required.
    if (privateKey == null) {
      throw new RuntimeException("MissingPrivateKeyException");
    }
    if (privateKey instanceof BCECPrivateKey) {
      ECDSASigner signer = new ECDSASigner(new HMacDSAKCalculator(new SHA256Digest()));
      ECPrivateKeyParameters privateKeyParams = new ECPrivateKeyParameters(((BCECPrivateKey) privateKey).getD(), CURVE);
      signer.init(true, privateKeyParams);
      BigInteger[] components = signer.generateSignature(input);
      return new ECDSASignature(components[0], components[1]).toCanonicalised();
    } else {
      try {
        final Signature ecSig = ECSignatureFactory.getRawInstance(provider);
        ecSig.initSign(privateKey);
        ecSig.update(input);
        final byte[] derSignature = ecSig.sign();
        return ECDSASignature.decodeFromDER(derSignature).toCanonicalised();
      } catch (SignatureException | InvalidKeyException ex) {
        throw new RuntimeException("ECKey signing error", ex);
      }
    }
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

  public static class ECDSASignature {
    /**
     * The two components of the signature.
     */
    public final BigInteger r, s;
    public byte v;

    /**
     * Constructs a signature with the given components. Does NOT automatically canonicalise the signature.
     *
     * @param r -
     * @param s -
     */
    public ECDSASignature(BigInteger r, BigInteger s) {
      this.r = r;
      this.s = s;
    }

    /**
     *t
     * @param r
     * @param s
     * @return -
     */
    private static ECDSASignature fromComponents(byte[] r, byte[] s) {
      return new ECDSASignature(new BigInteger(1, r), new BigInteger(1, s));
    }

    /**
     *
     * @param r -
     * @param s -
     * @param v -
     * @return -
     */
    public static ECDSASignature fromComponents(byte[] r, byte[] s, byte v) {
      ECDSASignature signature = fromComponents(r, s);
      signature.v = v;
      return signature;
    }

    public boolean validateComponents() {
      return validateComponents(r, s, v);
    }

    public static boolean validateComponents(BigInteger r, BigInteger s, byte v) {

      if (v != 27 && v != 28) return false;

      if (isLessThan(r, BigInteger.ONE)) return false;
      if (isLessThan(s, BigInteger.ONE)) return false;

      if (!isLessThan(r, SECP256K1N)) return false;
      if (!isLessThan(s, SECP256K1N)) return false;

      return true;
    }

    public static ECDSASignature decodeFromDER(byte[] bytes) {
      ASN1InputStream decoder = null;
      try {
        decoder = new ASN1InputStream(bytes);
        DLSequence seq = (DLSequence) decoder.readObject();
        if (seq == null)
          throw new RuntimeException("Reached past end of ASN.1 stream.");
        ASN1Integer r, s;
        try {
          r = (ASN1Integer) seq.getObjectAt(0);
          s = (ASN1Integer) seq.getObjectAt(1);
        } catch (ClassCastException e) {
          throw new IllegalArgumentException(e);
        }
        // OpenSSL deviates from the DER spec by interpreting these values as unsigned, though they should not be
        // Thus, we always use the positive versions. See: http://r6.ca/blog/20111119T211504Z.html
        return new ECDSASignature(r.getPositiveValue(), s.getPositiveValue());
      } catch (IOException e) {
        throw new RuntimeException(e);
      } finally {
        if (decoder != null)
          try { decoder.close(); } catch (IOException x) {}
      }
    }

    /**
     * Will automatically adjust the S component to be less than or equal to half the curve order, if necessary.
     * This is required because for every signature (r,s) the signature (r, -s (mod N)) is a valid signature of
     * the same message. However, we dislike the ability to modify the bits of a Ethereum transaction after it's
     * been signed, as that violates various assumed invariants. Thus in future only one of those forms will be
     * considered legal and the other will be banned.
     *
     * @return  -
     */
    public ECDSASignature toCanonicalised() {
      if (s.compareTo(HALF_CURVE_ORDER) > 0) {
        // The order of the curve is the number of valid points that exist on that curve. If S is in the upper
        // half of the number of valid points, then bring it back to the lower half. Otherwise, imagine that
        //    N = 10
        //    s = 8, so (-8 % 10 == 2) thus both (r, 8) and (r, 2) are valid solutions.
        //    10 - 8 == 2, giving us always the latter solution, which is canonical.
        return new ECDSASignature(r, CURVE.getN().subtract(s));
      } else {
        return this;
      }
    }

    /**
     *
     * @return -
     */
    public String toBase64() {
      byte[] sigData = new byte[65];  // 1 header + 32 bytes for R + 32 bytes for S
      sigData[0] = v;
      System.arraycopy(bigIntegerToBytes(this.r, 32), 0, sigData, 1, 32);
      System.arraycopy(bigIntegerToBytes(this.s, 32), 0, sigData, 33, 32);
      return new String(Base64.encode(sigData), Charset.forName("UTF-8"));
    }

    public byte[] toByteArray() {
      final byte fixedV = this.v >= 27
          ? (byte) (this.v - 27)
          :this.v;

      return ByteUtil.merge(
          bigIntegerToBytes(this.r),
          bigIntegerToBytes(this.s),
          new byte[]{fixedV});
    }

    public String toHex() {
      return Hex.toHexString(toByteArray());
    }

    @Override
    public boolean equals(Object o) {
      if (this == o) return true;
      if (o == null || getClass() != o.getClass()) return false;

      ECDSASignature signature = (ECDSASignature) o;

      if (!r.equals(signature.r)) return false;
      if (!s.equals(signature.s)) return false;

      return true;
    }

    @Override
    public int hashCode() {
      int result = r.hashCode();
      result = 31 * result + s.hashCode();
      return result;
    }
  }


  public byte[] getPrivKeyBytes() {
    if (privateKey == null) {
      return null;
    }
    return bigIntegerToBytes(((BCECPrivateKey)privateKey).getD(), 32);
  }

}
