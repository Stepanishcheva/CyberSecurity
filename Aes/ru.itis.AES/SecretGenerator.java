package ru.itis.AES;
import java.util.Arrays;

public class SecretGenerator {
  private final Encryptor encryptor;

  public SecretGenerator(Encryptor encryptor) {
    this.encryptor = encryptor;
  }

  public byte[] getSecret() throws Exception {
    byte[] encrypted = encryptor.encrypt(new byte[0]);
    int firstBlock = encrypted.length / 16;
    int i = 0;
    while (encrypted.length / 16 == firstBlock) {
      i++;
      encrypted = encryptor.encrypt(new byte[i]);
    }
    int secretLength = firstBlock * 16 - i;
    byte[] b = new byte[(secretLength / 16 + 1) * 16 - 1];
    byte[] secret = new byte[secretLength];
    for (int j = 0; j < secretLength; j++) {
      secret[j] = resolveByte(j,secret,b);
      b = new byte[b.length - 1];
    }
    return secret;
  }

  private byte resolveByte(int secretSize, byte[] secret, byte[] byteArray) throws Exception{
    byte[] encrypted = encryptor.encrypt(byteArray);
    byte[] encryptShortFirst = new byte[byteArray.length + secretSize + 1];
    System.arraycopy(encrypted, 0, encryptShortFirst, 0, encryptShortFirst.length);
    byte[] array = new byte[byteArray.length + secretSize + 1];
    System.arraycopy(byteArray, 0, array, 0, byteArray.length);
    System.arraycopy(secret, 0, array, byteArray.length, secretSize);
    for (int i = -128; i <= 127; i++) {
      array[array.length - 1] = (byte) i;
      byte[] result = encryptor.encrypt(array);
      byte[] encryptShortLst = new byte[byteArray.length + secretSize + 1];
      System.arraycopy(result, 0, encryptShortLst, 0, encryptShortLst.length);
      if (Arrays.equals(encryptShortLst, encryptShortFirst)) {
        return (byte) i;
      }
    }
    throw new Exception("Not found");
  }
}
