package ru.itis.AES;
import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.Key;

public class Encryptor {
  private static final String secret = "12345";
  private final Cipher cipher;

  public Encryptor() throws Exception {
      byte[] init = new byte[16];
      for (int i = 0; i < 16; i++) {
        init[i] = 1;
      }
      IvParameterSpec ivParameterSpec = new IvParameterSpec(init);
      cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
      String keyStr = "someString16Leng";
      Key secret = new SecretKeySpec(keyStr.getBytes(), "AES");
      cipher.init(Cipher.ENCRYPT_MODE, secret, ivParameterSpec);
  }

  public byte[] encrypt(byte[] byteArray) throws Exception {
    int secretLength = secret.getBytes().length;
    byte[] fullByteArray = new byte[byteArray.length + secretLength];
    System.arraycopy(byteArray, 0, fullByteArray, 0, byteArray.length);
    System.arraycopy(secret.getBytes(), 0, fullByteArray, byteArray.length, secretLength);
    return cipher.doFinal(fullByteArray);
  }
}
