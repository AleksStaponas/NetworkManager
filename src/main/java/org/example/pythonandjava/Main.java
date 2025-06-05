package org.example.pythonandjava;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.nio.file.Paths;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        // Use absolute path to login.fxml
        Parent root = FXMLLoader.load(Paths.get("C:/PythonAndJava/src/main/res/FXMLFiles/login.fxml").toUri().toURL());

        Scene scene = new Scene(root);

        // Use absolute path to style.css
        scene.getStylesheets().add(Paths.get("C:/PythonAndJava/src/main/res/cssStyles/style.css").toUri().toString());

        primaryStage.setTitle("Login");
        primaryStage.setScene(scene);
        primaryStage.setMaximized(false);
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
