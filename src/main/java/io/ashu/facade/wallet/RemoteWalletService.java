package io.ashu.facade.wallet;

import io.ashu.core.model.Account;
import io.ashu.core.model.Transaction;
import io.ashu.wallet.TransactionResult;
import io.ashu.wallet.Wallet;
import java.util.concurrent.Future;

public class RemoteWalletService implements Wallet {

  @Override
  public boolean submitTransaction(Transaction trx) {
    return false;
  }

  @Override
  public Future<TransactionResult> callTransaction(Transaction trx) {
    return null;
  }

  @Override
  public Account queryAccount(byte[] id) {
    return null;
  }
}
