package io.ashu.core.model;


import io.ashu.core.model.Transaction.Type;
import io.ashu.core.model.transaction.SendCoinTransaction;

public class TransactionFactory {

  Transaction create(Type type, byte[] data) {
    switch (type) {
      case TRANSFER:
        return new SendCoinTransaction(data);

    }

    return null;
  }

}
