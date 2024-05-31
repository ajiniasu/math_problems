package logicmaster.app.TaskList;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class Task4Controller {

    @FXML
    private Label functionVectorLabel;

    @FXML
    private VBox optionsContainer;

    @FXML
    private Button resetButton;

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

    // Массив с названиями функций
    private static final String[] FUNCTION_NAMES = {
            "Конъюнкция",
            "Дизъюнкция",
            "Сложение",
            "Штрих",
            "Стрелка",
            "Импликация",
            "Эквивалентность",
            "Коимпликация",
            "Обратная импликация",
            "Обратная коимпликация"
    };

    // Маппинг векторов функций к их названиям
    private static final Map<String, int[]> FUNCTION_VECTORS = new HashMap<>();

    static {
        FUNCTION_VECTORS.put("Конъюнкция", new int[]{0, 0, 0, 1});
        FUNCTION_VECTORS.put("Дизъюнкция", new int[]{0, 1, 1, 1});
        FUNCTION_VECTORS.put("Сложение", new int[]{0, 1, 1, 0});
        FUNCTION_VECTORS.put("Штрих", new int[]{1, 1, 1, 0});
        FUNCTION_VECTORS.put("Стрелка", new int[]{1, 0, 0, 0});
        FUNCTION_VECTORS.put("Импликация", new int[]{1, 1, 0, 1});
        FUNCTION_VECTORS.put("Эквивалентность", new int[]{1, 0, 0, 1});
        FUNCTION_VECTORS.put("Коимпликация", new int[]{0, 0, 1, 0});
        FUNCTION_VECTORS.put("Обратная импликация", new int[]{1, 0, 1, 1});
        FUNCTION_VECTORS.put("Обратная коимпликация", new int[]{0, 1, 0, 0});
    }

    // Метод инициализации, вызывается при загрузке FXML
    public void initialize() {
        handleReset(); // Изначальный сброс игры
    }

    // Обработчик нажатия кнопки "Попытка"
    @FXML
    private void handleGuess(ActionEvent event) {
        Button clickedButton = (Button) event.getSource();
        String guessedFunction = clickedButton.getText();
        String actualFunction = getActualFunction();

        // Проверяем правильность ответа и выводим результат
        if (guessedFunction.equals(actualFunction)) {
            resultLabel.setText("Правильно!");
        } else {
            resultLabel.setText("Неправильно. Правильный ответ: " + actualFunction);
        }

        // Отключаем кнопки после ответа
        disableButtons(true);
        resetButton.setDisable(false);
    }

    // Метод для сброса игры
    @FXML
    private void handleReset() {
        // Генерируем случайный индекс функции
        Random random = new Random();
        int index = random.nextInt(FUNCTION_NAMES.length);
        String functionName = FUNCTION_NAMES[index];
        int[] functionVector = FUNCTION_VECTORS.get(functionName);

        // Отображаем вектор функции на экране
        StringBuilder vectorText = new StringBuilder("Вектор функции: ");
        for (int value : functionVector) {
            vectorText.append(value).append(" ");
        }
        functionVectorLabel.setText(vectorText.toString());

        // Отображаем варианты названий функций в виде кнопок
        optionsContainer.getChildren().clear(); // Очищаем предыдущие кнопки
        for (String name : FUNCTION_NAMES) {
            Button button = new Button(name);
            button.setOnAction(this::handleGuess);
            optionsContainer.getChildren().add(button);
        }

        // Включаем кнопки перед началом игры
        disableButtons(false);
        resetButton.setDisable(true);
        resultLabel.setText("");
    }

    // Метод для получения фактического названия функции по вектору
    private String getActualFunction() {
        int[] functionVector = new int[4]; // Вектор текущей функции
        String actualFunction = ""; // Фактическое название функции

        // Получаем вектор текущей функции
        String[] vectorStr = functionVectorLabel.getText().split(" ");
        for (int i = 0; i < 4; i++) {
            functionVector[i] = Integer.parseInt(vectorStr[i + 2]); // Начинаем с индекса 2, чтобы пропустить "Вектор функции:"
        }

        // Сравниваем вектор с маппингом функций
        for (Map.Entry<String, int[]> entry : FUNCTION_VECTORS.entrySet()) {
            if (compareVectors(entry.getValue(), functionVector)) {
                actualFunction = entry.getKey();
                break;
            }
        }

        return actualFunction;
    }

    // Метод для сравнения векторов функций
    private boolean compareVectors(int[] vector1, int[] vector2) {
        for (int i = 0; i < vector1.length; i++) {
            if (vector1[i] != vector2[i]) {
                return false;
            }
        }
        return true;
    }

    // Метод для включения или отключения всех кнопок
    private void disableButtons(boolean disable) {
        for (Node node : optionsContainer.getChildren()) {
            if (node instanceof Button) {
                ((Button) node).setDisable(disable);
            }
        }
    }
}
