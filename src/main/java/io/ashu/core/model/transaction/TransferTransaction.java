package io.ashu.core.model.transaction;

import io.ashu.core.model.AbstractTransaction;
import io.ashu.core.model.Account;
import io.ashu.db.store.AccountStore;
import org.spongycastle.util.encoders.Hex;
import org.springframework.beans.factory.annotation.Autowired;

public class TransferTransaction extends AbstractTransaction {

  private byte[] toAddress;
  private long amount;

  @Autowired
  private AccountStore accountStore;

  private Account receiver;

  public TransferTransaction() {
    receiver = accountStore.getAccount(toAddress);
  }

  @Override
  public void validate() {

    if(this.getOwner() == null) {
      throw new RuntimeException("Send doesn't exist");
    }

    long senderBalance = accountStore.getBalance(this.getOwnerAddress());

    if (amount < senderBalance) {
      throw new RuntimeException("Balance of sender should more than amount");
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

  public TransferTransaction(byte[] data) {
    super();
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
