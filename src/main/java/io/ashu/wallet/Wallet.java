package io.ashu.wallet;

import io.ashu.core.model.Transaction;
import io.ashu.core.model.Account;
import java.util.concurrent.Future;

public interface Wallet {
  // API
  boolean submitTransaction(Transaction trx);
  Future<TransactionResult> callTransaction(Transaction trx);
  Account queryAccount(byte[] id);

}
