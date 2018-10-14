package io.ashu.wallet;

import io.ashu.core.model.AbstractTransaction;
import io.ashu.core.model.Account;
import java.util.concurrent.Future;

public interface Wallet {
  // API
  boolean submitTransaction(AbstractTransaction trx);
  Future<TransactionResult> callTransaction(AbstractTransaction trx);
  Account queryAccount(byte[] id);

}
