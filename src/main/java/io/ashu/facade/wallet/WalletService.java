package io.ashu.facade.wallet;

import io.ashu.config.AppConfig;
import io.ashu.core.model.AbstractTransaction;
import io.ashu.core.model.Account;
import io.ashu.db.store.AccountStore;
import io.ashu.db.store.impl.SimpleAccountStore;
import io.ashu.wallet.TransactionResult;
import io.ashu.wallet.Wallet;
import java.util.concurrent.Future;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Service;

@Service
public class WalletService implements Wallet {

  private AccountStore accountStore = SimpleAccountStore.getInstance();

  public WalletService() {
//    AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
//    accountStore = context.getBean(SimpleAccountStore.class);
  }

  @Override
  public boolean submitTransaction(AbstractTransaction trx) {
    return false;
  }

  @Override
  public Future<TransactionResult> callTransaction(AbstractTransaction trx) {
    return null;
  }

  @Override
  public Account queryAccount(byte[] id) {
    return accountStore.getAccount(id);
  }
}
