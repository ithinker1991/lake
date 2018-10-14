package io.ashu.core.model;

import com.alibaba.fastjson.JSON;
import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.spongycastle.util.encoders.Hex;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Account {
  private byte[] id;
  private String name;
  private long balance;

  @Override
  public String toString() {
    return "Account{" +
        "id=" + Hex.toHexString(id) +
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
