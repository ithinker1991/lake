package io.ashu.exception;

class LakeException extends RuntimeException {
  LakeException(String msg) {
    super(msg);
  }

  private LakeException error(String title, String message, Object... args) {
    return new LakeException(title + ": " + String.format(message, args));
  }
}
