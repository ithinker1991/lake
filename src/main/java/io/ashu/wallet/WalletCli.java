package io.ashu.wallet;

import io.ashu.core.model.Account;
import io.ashu.core.model.Transaction;
import java.util.concurrent.Future;

public interface WalletCli {
  // API
  boolean submitTransaction(Transaction trx);
  Future<TransactionResult> callTransaction(Transaction trx);
  Account queryAccount(byte[] id);

}
