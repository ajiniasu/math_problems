package logicmaster.app.TaskList;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Arrays;

public class Task2Controller {

    @FXML
    private TextField functionVectorInput;

    @FXML
    private TextField argIndexInput;

    @FXML
    private Label resultLabel;

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
    private void handleGenerateResidualFunction(ActionEvent event) {
        // Получаем введенные пользователем значения
        String vectorInput = functionVectorInput.getText();
        String indexInput = argIndexInput.getText();

        try {
            // Преобразуем строковые значения в массив и целочисленное значение
            int[] functionVector = new int[vectorInput.length()];
            for (int i = 0; i < vectorInput.length(); i++) {
                functionVector[i] = Character.getNumericValue(vectorInput.charAt(i));
            }
            int argIndex = Integer.parseInt(indexInput);

            // Генерируем остаточную функцию
            int[] residualFunction = generateResidualFunction(functionVector, argIndex);

            // Выводим результат в Label
            StringBuilder result = new StringBuilder();
            for (int value : residualFunction) {
                result.append(value);
            }
            resultLabel.setText(result.toString());
        } catch (NullPointerException | IllegalArgumentException e) {
            resultLabel.setText("Ошибка: некорректный ввод данных");
        }
    }

    // Функция для генерации остаточной функции
    private int[] generateResidualFunction(int[] functionVector, int argIndex) {
        // Проверяем корректность входных данных
        if (argIndex < 0 || argIndex >= functionVector.length) {
            throw new IllegalArgumentException("Invalid argument index!");
        }

        // Создаем массив для хранения остаточной функции
        int[] residualFunction = new int[functionVector.length - 1];

        // Копируем значения функции до указанного аргумента
        System.arraycopy(functionVector, 0, residualFunction, 0, argIndex);

        // Копируем значения функции после указанного аргумента
        System.arraycopy(functionVector, argIndex + 1, residualFunction, argIndex, functionVector.length - argIndex - 1);

        return residualFunction;
    }
}
