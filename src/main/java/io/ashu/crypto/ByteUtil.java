package io.ashu.crypto;

public class ByteUtil {
    public static byte[] merge(byte[] ... arrays) {
        int length = 0;
        for (byte[] array: arrays) {
            length += array.length;
        }
        byte[] mergedArray = new byte[length];
        int start = 0;
        for (byte[] array: arrays) {
           System.arraycopy(array, 0, mergedArray, start, array.length);
           start += array.length;
        }
        return mergedArray;
    }


}
