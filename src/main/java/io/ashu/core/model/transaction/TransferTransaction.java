package io.ashu.core.model.transaction;

import io.ashu.core.model.Transaction;
import io.ashu.core.model.Account;
import io.ashu.db.store.AccountStore;
import io.ashu.db.store.impl.SimpleAccountStore;
import io.ashu.exception.TransactionException;
import org.spongycastle.util.encoders.Hex;

public class TransferTransaction extends Transaction {

  private AccountStore accountStore = SimpleAccountStore.getInstance();

  private byte[] toAddress;
  private long amount;

  private Account receiver;

  public TransferTransaction(byte[] ownerAddress, byte[] toAddress, long amount) {
    super(ownerAddress);
    this.toAddress = toAddress;
    receiver = accountStore.getAccount(this.toAddress);
    this.amount = amount;
  }

  @Override
  public void validate() {

    if(this.getOwner() == null) {
      throw TransactionException.validationError("sender doesn't exist");
    }

    long senderBalance = accountStore.getBalance(this.getOwnerAddress());

    if (amount > senderBalance) {
      throw TransactionException.validationError("balance of sender should more than amount");
    }
  }

  @Override
  public void execute() {
    if (this.receiver == null) {
      this.receiver = new Account();
    }
    long recBalance = this.receiver.getBalance() + amount;
    long senderBalance = this.getOwner().getBalance() - amount;

    this.receiver.setBalance(recBalance);
    this.getOwner().setBalance(senderBalance);
    accountStore.putAccount(toAddress, this.receiver);
    accountStore.putAccount(this.getOwnerAddress(), this.getOwner());
  }


  @Override
  public String toString() {
    return "TransferTransaction{" +
        "sender=" + Hex.toHexString(this.getOwnerAddress()) +
        "receiver=" + Hex.toHexString(toAddress) +
        ", amount=" + amount +
        '}';
  }

  public static void main(String[] args) {

  }
}
