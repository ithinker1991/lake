package io.ashu.core.model.transaction;

import io.ashu.core.model.AbstractTransaction;
import io.ashu.core.model.Account;
import io.ashu.db.store.AccountStore;
import io.ashu.db.store.impl.SimpleAccountStore;
import java.util.Map;
import lombok.Setter;

public class GenesisTransaction extends AbstractTransaction {

  private AccountStore accountStore = SimpleAccountStore.getInstance();

  @Setter
  private Map<byte[], Long> genesisAccounts;

  @Override
  public void validate() {

  }

  @Override
  public void execute() {
    genesisAccounts.forEach((id, balance) -> {
      Account account = new Account();
      account.setId(id);
      account.setBalance(balance);
      accountStore.putAccount(id, account);
    });
  }
}
