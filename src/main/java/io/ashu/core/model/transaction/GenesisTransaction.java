package io.ashu.core.model.transaction;

import io.ashu.core.model.Transaction;
import io.ashu.core.model.Account;
import io.ashu.db.store.AccountStore;
import io.ashu.db.store.impl.SimpleAccountStore;
import java.util.Map;
import lombok.Setter;

public class GenesisTransaction extends Transaction {

  private AccountStore accountStore = SimpleAccountStore.getInstance();

  @Setter
  private Map<byte[], Long> genesisAccounts;

  public GenesisTransaction(byte[] ownerAddress) {
    super(ownerAddress);
  }

  @Override
  public void validate() {

  }

  @Override
  public void execute() {
    genesisAccounts.forEach((address, balance) -> {
      Account account = new Account();
      account.setAddress(address);
      account.setBalance(balance);
      accountStore.putAccount(address, account);
    });
  }
}
