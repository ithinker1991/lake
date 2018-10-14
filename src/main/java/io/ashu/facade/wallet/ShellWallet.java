package io.ashu.facade.wallet;

import io.ashu.core.model.Account;
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
  @Getter
  @Setter
  private byte[] privKey;


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

  public TransactionCommands(WalletService wallet, ConsoleService console) {
    this.wallet = wallet;
    this.console = console;
  }

  @ShellMethod("queryAccount <id>")
  public void queryAccount(String id) {
    Account account = wallet.queryAccount(Hex.decode(id));
    console.write(account.toString());
  }

//  @Override
//  public boolean submitTransaction(Transaction trx) {
//    return false;
//  }
//
//  @Override
//  public Future<TransactionResult> callTransaction(Transaction trx) {
//    return null;
//  }
//
//  @Override
//  public Account queryAccount(byte[] id) {
//    return null;
//  }
//



}
