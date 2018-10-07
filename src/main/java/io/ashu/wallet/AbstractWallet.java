package io.ashu.wallet;

import io.ashu.core.model.Account;
import io.ashu.core.model.Transaction;
import java.util.concurrent.Future;

public class AbstractWallet implements Wallet {
  private byte[] address;
  private byte[] priKey;


  @Override
  public boolean submitTransaction(Transaction tx) {
    return false;
  }

  @Override
  public Future<TransactionResult> callTransaction(Transaction tx) {
    return null;
  }

  @Override
  public Account queryAccount(byte[] id) {
    return null;
  }
}
