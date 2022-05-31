package ru.itis.AES;
public class Main {
  public static void main(String[] args) throws Exception{
    Encryptor encryptor = new Encryptor();
    SecretGenerator secretGenerator = new SecretGenerator(encryptor);
    byte[] secret = secretGenerator.getSecret();
    System.out.println(new String(secret));
  }
}
