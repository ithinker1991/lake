package io.ashu.core.model;

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
}
