package io.ashu.exception;

import org.ethereum.solidity.compiler.ContractException;

public class TransactionException extends RuntimeException {
  public TransactionException(String msg) {
    super(msg);
  }

  public static TransactionException validationError(String msg, Object... args) {
    return error("transaction validation error", msg, args);
  }

  public static TransactionException executionError(String msg, Object... args) {
    return error("transaction execution error", msg, args);
  }

  private static TransactionException error(String title, String message, Object... args) {
    return new TransactionException(title + ": " + String.format(message, args));
  }
}