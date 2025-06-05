package org.example.pythonandjava;

import javafx.animation.ScaleTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;
import org.python.util.PythonInterpreter;

public class MainWindowController {

    @FXML
    private Button connectedDevicesButton;

    @FXML
    private Button runPythonButton;

    @FXML
    private Node box1;
    @FXML
    private Node box2;
    @FXML
    private Node box3;

    @FXML
    private ImageView MinimiseIcon;

    @FXML
    private ImageView CloseIcon;

    @FXML
    private AnchorPane myPane;

    private final PythonInterpreter interpreter = new PythonInterpreter();

    private static final Duration ANIM_DURATION = Duration.millis(150);

    @FXML
    private void runNetworkPortScan() {
        try {
            interpreter.execfile("C:/PythonAndJava/src/main/java/PythonScripts/openPortScan.py");
            runPythonButton.setText("Script Run!");
        } catch (Exception e) {
            runPythonButton.setText("Error running script");
            e.printStackTrace();
        }
    }

    @FXML
    private void runConnectedDevices() {
        try {
            interpreter.execfile("C:/PythonAndJava/src/main/java/PythonScripts/ConnectedDevices.py");
            runPythonButton.setText("Script Run!");
        } catch (Exception e) {
            runPythonButton.setText("Error running script");
            e.printStackTrace();
        }
    }

    private void animateScale(Node node, double scaleX, double scaleY) {
        ScaleTransition st = new ScaleTransition(ANIM_DURATION, node);
        st.setToX(scaleX);
        st.setToY(scaleY);
        st.playFromStart();
    }
    @FXML
    private void MinimiseHandler(MouseEvent event){
        Node source = (Node) event.getSource();
        Stage stage = (Stage) source.getScene().getWindow();
        stage.setIconified(true);
    }

    @FXML
    private void closeHandler(MouseEvent event) {
        Node source = (Node) event.getSource();
        Stage stage = (Stage) source.getScene().getWindow();
        stage.close();
    }

    @FXML
    private void hoverEntered1() {
        animateScale(box1, 1.1, 1.1);
    }

    @FXML
    private void hoverExitBox1() {
        animateScale(box1, 1.0, 1.0);
    }

    @FXML
    private void hoverEntered2() {
        animateScale(box2, 1.1, 1.1);
    }

    @FXML
    private void hoverExitBox2() {
        animateScale(box2, 1.0, 1.0);
    }

    @FXML
    private void hoverEntered3() {
        animateScale(box3, 1.1, 1.1);
    }

    @FXML
    private void hoverExitBox3() {
        animateScale(box3, 1.0, 1.0);
    }
}
