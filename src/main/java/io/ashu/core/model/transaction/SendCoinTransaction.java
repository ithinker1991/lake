package io.ashu.core.model.transaction;

import io.ashu.core.model.Transaction;
import org.spongycastle.util.encoders.Hex;

public class SendCoinTransaction extends Transaction {

  @Override
  public void validate() {

  }

  @Override
  public void excute() {

  }
  private byte[] toAddress;

  public SendCoinTransaction(byte[] data) {
    super();
  }

  @Override
  public String toString() {
    return "SendCoinTransaction{" +
        "ownerAddress=" + Hex.toHexString(getOwnerAddress()) +
        "toAddress=" + Hex.toHexString(toAddress) +
        '}';
  }



}
