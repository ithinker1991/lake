package io.ashu.facade.wallet;

import io.ashu.core.model.Transaction;
import io.ashu.core.model.Account;
import io.ashu.core.model.BlockChain;
import io.ashu.db.store.AccountStore;
import io.ashu.db.store.impl.SimpleAccountStore;
import io.ashu.wallet.TransactionResult;
import io.ashu.wallet.Wallet;
import java.util.concurrent.Future;
import org.springframework.stereotype.Service;

@Service
public class WalletService implements Wallet {

  private AccountStore accountStore = SimpleAccountStore.getInstance();
  private BlockChain blockChain;


  public WalletService() {
    this.blockChain = BlockChain.getInstance();
  }

  @Override
  public boolean submitTransaction(Transaction trx) {
    this.blockChain.pushTransaction(trx);
    return true;
  }

  @Override
  public Future<TransactionResult> callTransaction(Transaction trx) {
    return null;
  }

  @Override
  public Account queryAccount(byte[] address) {
    return accountStore.getAccount(address);
  }
}
