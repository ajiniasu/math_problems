package logicmaster.app.TaskList;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.scene.Node;

import java.io.IOException;

public class Task1Controller {

    @FXML
    private TextField argumentField;

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


    // Обработчик события для кнопки генерации булевой функции
    @FXML
    private void handleGenerateFunctionButtonClick(ActionEvent event) {
        // Получаем количество аргументов из текстового поля
        String userInput = argumentField.getText().trim();

        // Проверяем, является ли ввод пользователя целым числом
        if (!userInput.matches("^-?\\d+$")) {
            resultLabel.setText("Введите целое число");
            return;
        }

        int n = Integer.parseInt(userInput);

        // Проверяем, что число положительное
        if (n < 0) {
            resultLabel.setText("Введите неотрицательное число");
            return;
        }

        // Генерируем булеву функцию и выводим результат
        String result = generateBooleanFunction(n);
        resultLabel.setText(result);
    }

    // Функция для генерации и оценки булевой функции
    private String generateBooleanFunction(int n) {
        StringBuilder result = new StringBuilder();
        // Вычисляем количество всех возможных комбинаций значений для n аргументов
        int totalCombinations = (int) Math.pow(2, n);

        // Генерируем и записываем значения булевой функции для всех возможных комбинаций значений
        for (int i = 0; i < totalCombinations; i++) {
            // Преобразуем число i в двоичное представление
            String binary = Integer.toBinaryString(i);

            // Дополняем нулями до длины n
            while (binary.length() < n) {
                binary = "0" + binary;
            }

            // Оцениваем значение булевой функции для текущей комбинации аргументов
            boolean value = evaluateBooleanFunction(binary);

            // Добавляем значение в результат
            result.append(value ? "1" : "0");
        }
        return result.toString();
    }

    // Функция для оценки значения булевой функции от заданных аргументов
    private boolean evaluateBooleanFunction(String binary) {
        // Пример простой булевой функции - "И" (AND)
        // Можете заменить данную функцию на свою в зависимости от требований
        // В данном примере просто вычисляем "И" (AND) для всех аргументов
        for (int i = 0; i < binary.length(); i++) {
            if (binary.charAt(i) != '1') {
                return false;
            }
        }
        return true;
    }

}
