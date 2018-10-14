package io.ashu.core.model;


import io.ashu.core.model.AbstractTransaction.Type;
import io.ashu.core.model.transaction.TransferTransaction;

public class TransactionFactory {

  AbstractTransaction create(Type type, byte[] data) {
    switch (type) {
      case TRANSFER:
        return new TransferTransaction(data);

    }

    return null;
  }

}
