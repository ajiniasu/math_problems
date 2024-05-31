package logicmaster.app.TaskList;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Random;

public class Task5Controller {

    private static final String[] VARIABLE_NAMES = {"Переманная 1", "Переменная 2"};
    private static final Random random = new Random();

    @FXML
    private Label functionVectorLabel;

    @FXML
    private CheckBox variable1Checkbox;

    @FXML
    private CheckBox variable2Checkbox;

    @FXML
    private Button submitButton;

    @FXML
    private Button resetButton;

    @FXML
    private Label resultLabel;

    private int[] functionVector;


    @FXML
    private void initialize() {
        generateNewFunctionVector();
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
    private void handleSubmit() {
        boolean[] correctChoices = getEssentialVariables();

        boolean userChoice1 = variable1Checkbox.isSelected();
        boolean userChoice2 = variable2Checkbox.isSelected();

        if (userChoice1 == correctChoices[0] && userChoice2 == correctChoices[1]) {
            resultLabel.setText("Ваши выборы верны.");
        } else {
            StringBuilder sb = new StringBuilder();
            if (correctChoices[0]) {
                sb.append(VARIABLE_NAMES[0]).append(" ");
            }
            if (correctChoices[1]) {
                sb.append(VARIABLE_NAMES[1]);
            }
            resultLabel.setText("Неправильный выбор. Правильные существенные переменные: " + sb.toString());
        }
    }

    @FXML
    private void handleReset() {
        generateNewFunctionVector();
        resultLabel.setText("");
        variable1Checkbox.setSelected(false);
        variable2Checkbox.setSelected(false);
    }

    private void generateNewFunctionVector() {
        functionVector = generateFunctionVector();
        functionVectorLabel.setText("Function vector: " + generateFunctionVectorString());
    }

    private String generateFunctionVectorString() {
        StringBuilder sb = new StringBuilder();
        for (int value : functionVector) {
            sb.append(value).append(" ");
        }
        return sb.toString();
    }

    private int[] generateFunctionVector() {
        int[] vector = new int[VARIABLE_NAMES.length * 2];
        for (int i = 0; i < vector.length; i++) {
            vector[i] = random.nextInt(2);
        }
        return vector;
    }

    private boolean[] getEssentialVariables() {
        boolean[] essentialVariables = new boolean[VARIABLE_NAMES.length];
        for (int i = 0; i < VARIABLE_NAMES.length; i++) {
            for (int j = 0; j < 2; j++) {
                if (functionVector[i * 2 + j] == 1) {
                    essentialVariables[i] = true;
                    break;
                }
            }
        }
        return essentialVariables;
    }
}
