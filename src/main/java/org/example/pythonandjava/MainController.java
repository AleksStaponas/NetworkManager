package main.java.pythonandjava;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import javax.crypto.*;
import javax.crypto.spec.SecretKeySpec;
import java.io.*;
import java.nio.file.Paths;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.Scanner;

public class MainController {

    String URL = "jdbc:postgresql://localhost:5432/UserInfo";
    private static final String USER = "postgres";
    private static final String PASS = "postgres";

    @FXML
    private Label idStatus;

    @FXML
    private TextField txtUserName;

    @FXML
    private PasswordField txtPassword;

    @FXML
    private Button loginButton;

    @FXML
    private Label TrustQuotes;

    @FXML
    public void Login(ActionEvent event) {

        String id = txtUserName.getText();
        String password = txtPassword.getText();
        LocalDateTime time = LocalDateTime.now();
        String logPath = "/PythonAndJava/src/main/res/login_log.txt";

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(logPath, true))) {

            // Admin login with decryption option
            if ("admin".equals(id) && "admin".equals(password)) {
                System.out.println("Welcome back!");
                System.out.println("Enter 'Decrypt' to decrypt login_log.txt");
                Scanner scanner = new Scanner(System.in);
                String input = scanner.nextLine();
                if ("Decrypt".equals(input)) {
                    System.out.println("Decryption started now");
                    decrypted(
                            "1234567812345678",
                            logPath,
                            "/PythonAndJava/src/main/res/LoginLogs/login_log_decrypted.txt"
                    );
                } else {
                    System.out.println("Not a valid option");
                }
                return;
            }

            try (Connection connection = DriverManager.getConnection(URL, USER, PASS)) {
                System.out.println("Connected to server");
                
                String sql = "SELECT * FROM userinfo WHERE Id = ? AND password = ?";
                Statement stmt = connection.createStatement(sql);
                stmt.setString(1, id);
                stmt.setString(2, password);

                if (rs.next()) {
                    bw.write("Login Succeeded user: " + id + " Time: " + time);
                    bw.newLine();
                    idStatus.setText("Login success");
                    openMainWindow();
                    ((Stage) txtUserName.getScene().getWindow()).close();
                } else {
                    bw.write("Login Failed attempted user: " + id + " Time: " + time);
                    bw.newLine();
                    idStatus.setText("Login failed");
                }

                rs.close();
                stmt.close();

            } catch (SQLException e) {
                idStatus.setText("Server connection failed.");
                e.printStackTrace();
            }

            // Encrypt log file after writing
            encryptedFile(
                    "1234567812345678",
                    logPath,
                    "/PythonAndJava/src/main/res/Logs/login_log_encrypted.txt"
            );

        } catch (IOException e) {
            idStatus.setText("Error writing log.");
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void openMainWindow() {
        try {
            FXMLLoader loader = new FXMLLoader(Paths.get("/PythonAndJava/src/main/res/FXMLFiles/main.fxml").toUri().toURL());
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setTitle("Main Window");
            stage.setScene(new Scene(root));
            stage.setMaximized(true);
            stage.setResizable(true);
            stage.setIconified(true);
            stage.show();
        } catch (IOException e) {
            idStatus.setText("Error loading main window.");
            e.printStackTrace();
        }
    }

    public static void decrypted(String secretKey, String fileInputPath, String fileOutPath)
            throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IOException,
            IllegalBlockSizeException, BadPaddingException {

        SecretKeySpec key = new SecretKeySpec(secretKey.getBytes(), "AES");
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.DECRYPT_MODE, key);

        File fileInput = new File("/PythonAndJava/src/main/res/Logs/login_log_encrypted.txt");
        byte[] inputBytes = new byte[(int) fileInput.length()];
        try (FileInputStream inputStream = new FileInputStream(fileInput)) {
            inputStream.read(inputBytes);
        }

        byte[] outputBytes = cipher.doFinal(inputBytes);

        try (FileOutputStream outputStream = new FileOutputStream(fileOutPath)) {
            outputStream.write(outputBytes);
        }

        System.out.println("File successfully decrypted! New File: " + fileOutPath);
    }

    public static void encryptedFile(String secretKey, String fileInputPath, String fileOutPath)
            throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IOException,
            IllegalBlockSizeException, BadPaddingException {

        SecretKeySpec key = new SecretKeySpec(secretKey.getBytes(), "AES");
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.ENCRYPT_MODE, key);

        File fileInput = new File(fileInputPath);
        byte[] inputBytes = new byte[(int) fileInput.length()];
        try (FileInputStream inputStream = new FileInputStream(fileInput)) {
            inputStream.read(inputBytes);
        }

        byte[] outputBytes = cipher.doFinal(inputBytes);

        try (FileOutputStream outputStream = new FileOutputStream(fileOutPath)) {
            outputStream.write(outputBytes);
        }

        System.out.println("File successfully encrypted! New File: " + fileOutPath);
    }

    @FXML
    public void LoginHover(MouseEvent mouseEvent) {
        loginButton.setScaleX(1.1);
        loginButton.setScaleY(1.1);
    }

    @FXML
    public void LoginExit(MouseEvent event) {
        loginButton.setScaleX(1.0);
        loginButton.setScaleY(1.0);
    }

    @FXML
    public void TrustQuotes() {
        int randomNumTrust = (int) (Math.random() * 4);
        switch (randomNumTrust) {
            case 0 -> TrustQuotes.setText("No hint for you :)");
            case 1 -> TrustQuotes.setText("We will never ask for your password");
            case 2 -> TrustQuotes.setText("Your data is encrypted and secure");
            case 3 -> TrustQuotes.setText("Two factor authentication always recommended");
        }
    }

    @FXML
    public void initialize() {
        TrustQuotes.setWrapText(true);
        TrustQuotes();
    }
}
