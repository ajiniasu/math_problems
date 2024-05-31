package logicmaster.app.TaskList;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Task12Controller {

    @FXML
    private Label promptLabel;

    @FXML
    private TextField variablesField;

    @FXML
    private TextField rowsField;

    @FXML
    private Button submitButton;

    @FXML
    private Label resultLabel;

    @FXML
    private VBox inputFields;

    private boolean[][] truthTable;

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
    private void initialize() {
        promptLabel.setText("Введите количество переменных:");
    }

    @FXML
    private void handleSubmit() {
        try {
            int numVariables = Integer.parseInt(variablesField.getText());
            int numRows = Integer.parseInt(rowsField.getText());

            truthTable = new boolean[numRows][numVariables + 1]; // +1 для столбца результатов

            // Генерация текстовых полей для ввода строк таблицы истинности
            inputFields.getChildren().clear();
            for (int i = 0; i < numRows; i++) {
                TextField rowField = new TextField();
                rowField.setPromptText("Введите результаты для строки " + (i + 1));
                inputFields.getChildren().add(rowField);
            }

            // Очищаем результат
            resultLabel.setText("");

        } catch (NumberFormatException e) {
            showErrorAlert("Ошибка", "Пожалуйста, введите корректные числа.");
        }
    }

    @FXML
    private void handleDNF() {
        if (truthTable != null) {
            for (int i = 0; i < truthTable.length; i++) {
                String[] rowInputs = ((TextField) inputFields.getChildren().get(i)).getText().split(" ");
                if (rowInputs.length != truthTable[i].length) {
                    showErrorAlert("Ошибка", "Количество введенных значений не соответствует количеству переменных.");
                    return;
                }
                for (int j = 0; j < truthTable[i].length; j++) {
                    truthTable[i][j] = rowInputs[j].equals("1");
                }
            }
            showDNF(buildDNF(truthTable));
        } else {
            showErrorAlert("Ошибка", "Сначала введите количество переменных и строк таблицы истинности.");
        }
    }

    private void showDNF(List<String> dnf) {
        StringBuilder dnfString = new StringBuilder();
        for (String term : dnf) {
            dnfString.append(term).append(" + ");
        }
        // Удаляем последний символ "+"
        if (dnfString.length() > 0) {
            dnfString.delete(dnfString.length() - 3, dnfString.length());
        }
        resultLabel.setText("ДНФ: " + dnfString.toString());
    }

    private List<String> buildDNF(boolean[][] truthTable) {
        List<String> dnf = new ArrayList<>();
        int numVariables = truthTable[0].length - 1; // учитываем последний столбец с результатами функции
        Set<String> terms = new HashSet<>();

        for (int i = 0; i < truthTable.length; i++) {
            if (truthTable[i][numVariables]) { // проверяем последний столбец на истинность
                StringBuilder term = new StringBuilder();
                for (int j = 0; j < numVariables; j++) {
                    if (truthTable[i][j]) {
                        term.append((char) ('A' + j)); // используем A, B, C... для переменных
                    } else {
                        term.append((char) ('A' + j)).append("'"); // используем A', B', C'... для отрицания переменных
                    }
                }
                terms.add(term.toString());
            }
        }

        dnf.addAll(terms);
        return dnf;
    }

    private void showErrorAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
