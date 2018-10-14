package io.ashu.wallet;

import io.ashu.core.model.Account;
import io.ashu.core.model.AbstractTransaction;
import java.util.concurrent.Future;

public class AbstractWallet implements Wallet {
  private byte[] address;
  private byte[] priKey;


  @Override
  public boolean submitTransaction(AbstractTransaction tx) {
    return false;
  }

  @Override
  public Future<TransactionResult> callTransaction(AbstractTransaction tx) {
    return null;
  }

  @Override
  public Account queryAccount(byte[] id) {
    return null;
  }
}
