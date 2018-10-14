package io.ashu.wallet;

import io.ashu.core.model.AbstractTransaction;
import io.ashu.core.model.Account;
import io.ashu.facade.Lake;
import java.util.concurrent.Future;

public class LocalWallet implements Wallet {

  public LocalWallet(Lake Lake) {


  }

  @Override
  public boolean submitTransaction(AbstractTransaction transaction) {
    return false;
  }

  @Override
  public Future<TransactionResult> callTransaction(AbstractTransaction trx) {
    return null;
  }

  @Override
  public Account queryAccount(byte[] id) {
    return null;
  }
}
