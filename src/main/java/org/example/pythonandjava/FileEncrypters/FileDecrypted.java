package org.example.pythonandjava.FileEncrypters;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

public class FileDecrypted {
    public static void main(String[] args) {
        try {
            decrypted(
                    "1234567812345678",  // 16-byte AES key
                    "/PythonAndJava/src/main/res/login_log_encrypted.txt",
                    "C:/PythonAndJava/src/main/res/login_log_decrypted.txt"
            );
        } catch (Exception e) {
            e.printStackTrace(); // Always catch and print exceptions in main
        }
    }

    public static void decrypted(String secretKey, String fileInputPath, String fileOutPath)
        throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IOException,
            IllegalBlockSizeException, BadPaddingException{
        var key = new SecretKeySpec(secretKey.getBytes(), "AES");
        var cypher = Cipher.getInstance("AES");
        cypher.init(Cipher.DECRYPT_MODE, key);

        var fileInput = new File("C:/PythonAndJava/src/main/res/login_log_encrypted.txt");
        var inputStream = new FileInputStream(fileInput);
        var inputBytes = new byte[(int ) fileInput.length()];
        inputStream.read(inputBytes);

        byte[] outputBytes = cypher.doFinal(inputBytes);

        var fileEncryptOut = new File(fileOutPath);
        var outputStream = new FileOutputStream(fileEncryptOut);
        outputStream.write(outputBytes);

        inputStream.close();
        outputStream.close();

        System.out.println("File successfully decrypted!");
        System.out.println("New File: "+ fileOutPath);
    }
}


