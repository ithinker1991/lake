package io.ashu.crypto;

import java.math.BigInteger;

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

    public static byte[] bigIntegerToBytes(BigInteger b, int numBytes) {
        if (b == null)
            return null;
        byte[] bytes = new byte[numBytes];
        byte[] biBytes = b.toByteArray();
        int start = (biBytes.length == numBytes + 1) ? 1 : 0;
        int length = Math.min(biBytes.length, numBytes);
        System.arraycopy(biBytes, start, bytes, numBytes - length, length);
        return bytes;
    }

    public static byte[] bigIntegerToBytes(BigInteger value) {
        if (value == null)
            return null;

        byte[] data = value.toByteArray();

        if (data.length != 1 && data[0] == 0) {
            byte[] tmp = new byte[data.length - 1];
            System.arraycopy(data, 1, tmp, 0, tmp.length);
            data = tmp;
        }
        return data;
    }


}
