package io.ashu.crypto;

import java.math.BigInteger;

public class BigIntgerUtil {
  public static boolean isLessThan(BigInteger valueA, BigInteger valueB){
    return valueA.compareTo(valueB) < 0;
  }
}
