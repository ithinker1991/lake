package io.ashu.facade.wallet;

import io.ashu.core.model.Transaction;
import io.ashu.core.model.Account;
import io.ashu.core.model.transaction.TransferTransaction;
import io.ashu.crypto.ECKey;
import java.io.PrintStream;
import lombok.Getter;
import lombok.Setter;
import org.spongycastle.util.encoders.Hex;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.stereotype.Service;


@SpringBootApplication
public class ShellWallet  {
  public static void main(String[] args) {
    SpringApplication.run(ShellWallet.class, args);
  }
}

@Service
class ConsoleService {
  private final static String ANSI_RESET = "\u001B[0m";
  private final static String ANSI_YELLOW = "\u001B[33m";

  private final PrintStream out = System.out;

  public void write(String msg, String ...args) {
    this.out.print("> ");
    this.out.print(ANSI_YELLOW);
    this.out.printf(msg, (Object[]) args);
    this.out.print(ANSI_RESET);
    this.out.println();
  }
}

@ShellComponent
class TransactionCommands {
  private final WalletService wallet;
  private final ConsoleService console;

  @Getter
  @Setter
  private byte[] privateKey;


  private byte[] ownerAddress;
  @Setter
  private boolean login;

  @Setter
  @Getter
  ECKey ecKey;

  public TransactionCommands(WalletService wallet, ConsoleService console) {
    this.wallet = wallet;
    this.console = console;
  }

  @ShellMethod("import-wallet privateKey")
  public void importWallet(String priKey) {
    try {
      this.ecKey = ECKey.fromprivateKey(Hex.decode(priKey));
    } catch (Exception e) {
      console.write("Import wallet failed");
    }
    setLogin(true);
    console.write("Import wallet successfully");
  }

  @ShellMethod("get-address")
  public void getAddress() {
    if (this.login) {
      console.write("Your account address:" + Hex.toHexString(this.ecKey.getAddress()));
    } else {
      console.write("Please login first");
    }
  }

  @ShellMethod("transfer receiver_address amount")
  public void transfer(String toAddrss, long amount) {
    Transaction trx = new TransferTransaction(ecKey.getAddress(), Hex.decode(toAddrss), amount);
    wallet.submitTransaction(trx);
  }

  @ShellMethod("query-account <address>")
  public void queryAccount(String hexAddress) {
    Account account = wallet.queryAccount(Hex.decode(hexAddress));
    if (account == null) {
      console.write("No this account");
    } else {
      console.write(account.toString());
    }
  }

  @ShellMethod("list-account <address>")
  public void ListAccounts() {

  }


}
//
//@ShellComponent
//class QueryCommands {
//  private final WalletService wallet;
//  private final ConsoleService console;
//
//  public QueryCommands(WalletService wallet, ConsoleService console) {
//    this.wallet = wallet;
//    this.console = console;
//  }
//  @ShellMethod("queryAccount <address>")
//  public void queryAccount(String address) {
//    Account account = wallet.queryAccount(Hex.decode(address));
//    console.write(account.toString());
//  }

