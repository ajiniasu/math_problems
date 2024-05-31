package logicmaster.app.TaskList;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class Task8Controller implements Initializable {

    @FXML
    private TextField vectorInput;

    @FXML
    private TextField dimensionInput;

    @FXML
    private Button generateButton;

    @FXML
    private Label resultLabel;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Initialize method
    }

    private boolean isFullscreen = true;

    public void setFullscreen(boolean fullscreen) {
        isFullscreen = fullscreen;
    }

    @FXML
    private void handleBackButtonClick(ActionEvent event) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/logicmaster/app/TaskList.fxml"));
            Parent root = fxmlLoader.load();
            Scene scene = new Scene(root);

            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.setFullScreen(isFullscreen);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void generateFDNF(ActionEvent event) {
        int n = Integer.parseInt(dimensionInput.getText());
        String vec = vectorInput.getText();
        resultLabel.setText(fdnfBuilder(vec, n));
    }

    private String fdnfBuilder(String vec, int n) {
        int bitNum = 0;
        for (int i = 0; i < vec.length(); i++) {
            if (vec.charAt(i) == '1') {
                bitNum += 1;
            }
        }

        StringBuilder dnf = new StringBuilder();
        int bitCount = 0;
        for (int i = 0; i < vec.length(); i++) {
            if (vec.charAt(i) == '1') {
                bitCount += 1;

                int temp = i;
                StringBuilder str = new StringBuilder();
                for (int j = 0; j < n; j++) {
                    str.append((n - j));
                    str.append("x");
                    str.append((temp & 1) == 1 ? "" : "¬");
                    temp = temp >> 1;
                }
                dnf.append(str.reverse());

                if (bitCount < bitNum) {
                    dnf.append(" V ");
                }
            }
        }
        return dnf.toString();
    }
}
