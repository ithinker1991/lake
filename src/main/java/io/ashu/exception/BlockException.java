package io.ashu.exception;

import io.ashu.core.model.Block;

public class BlockException extends LakeException {
  private BlockException(String msg) {super(msg);}

  public static BlockException validationError(String msg, Object... args) {
    return error("block validation error", msg, args);
  }

  private static BlockException error(String title, String message, Object... args) {
    return new BlockException(title + ": " + String.format(message, args));
  }

}
