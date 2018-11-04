package io.ashu.core.model;

import com.alibaba.fastjson.JSON;
import java.io.UnsupportedEncodingException;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.spongycastle.util.encoders.Hex;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Account {
  private byte[] address;
  private String name;
  private long balance;

  @Override
  public String toString() {
    return "Account{" +
        "address=" + Hex.toHexString(address) +
        ", name='" + name + '\'' +
        ", balance=" + balance +
        '}';
  }

  public static Account deseriallize(byte[] data) {
    try {
      return JSON.parseObject(new String(data, "UTF-8"), Account.class);
    } catch (UnsupportedEncodingException e) {
      e.printStackTrace();
    }
    return null;
  }

  public byte[] seriallize() {
    String json = JSON.toJSONString(this);
    return json.getBytes();
  }

}
