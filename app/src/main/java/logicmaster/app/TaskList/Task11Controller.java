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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class Task11Controller {

    @FXML
    private Label systemVectorsLabel;

    @FXML
    private TextField userAnswerInput;

    @FXML
    private Label resultLabel;

    private final String[] prepositionalClasses = {"1", "2", "3", "4", "5"};

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
        Random rand = new Random();
        int n = rand.nextInt(2) + 2; // количество аргументов от 2 до 3 включительно
        int systemLength = rand.nextInt(5) + 2; // количество функций в наборе
        List<boolean[]> sys = new ArrayList<>();

        StringBuilder systemVectorsText = new StringBuilder("Дана система векторов:\n");
        // создаем и выводим систему
        for (int i = 0; i < systemLength; i++) {
            systemVectorsText.append("");
            sys.add(randomFunctionVector(n));
            for (boolean b : sys.get(i)) {
                systemVectorsText.append(b ? 1 : 0);
            }
            systemVectorsText.append("\n");
        }
        systemVectorsLabel.setText(systemVectorsText.toString());
    }

    @FXML
    private void checkAnswer(ActionEvent event) {
        Boolean[] systemClasses = checkSystemClasses();
        String userAnswer = userAnswerInput.getText().trim(); // Получить введенный текст и удалить лишние пробелы

        // Создать ожидаемый ответ в формате "1 2 3 4 5"
        StringBuilder expectedAnswer = new StringBuilder();
        for (int i = 0; i < 5; i++) {
            if (systemClasses[i]) {
                expectedAnswer.append(prepositionalClasses[i]).append(" ");
            }
        }
        String expectedAnswerStr = expectedAnswer.toString().trim(); // Удалить лишние пробелы

        // Сравнить введенный ответ с ожидаемым
        if (userAnswer.equals(expectedAnswerStr)) {
            resultLabel.setText("Верно");
        } else {
            resultLabel.setText("Неверно");
        }
    }

    @FXML
    public void resetGame(ActionEvent actionEventevent) {
        userAnswerInput.clear();
        resultLabel.setText("");
        initialize();
    }

    private Boolean[] checkSystemClasses() {
        List<boolean[]> sys = new ArrayList<>();
        String[] vectors = systemVectorsLabel.getText().split("\n");
        for (String vector : vectors) {
            boolean[] vec = new boolean[vector.length()];
            for (int i = 0; i < vector.length(); i++) {
                vec[i] = vector.charAt(i) == '1';
            }
            sys.add(vec);
        }

        Boolean[] classes = new Boolean[5];
        Arrays.fill(classes, true);

        // T0
        for (boolean[] s : sys) {
            if (!checkT0(s)) {
                classes[0] = false;
                break;
            }
        }
        // T1
        for (boolean[] s : sys) {
            if (!checkT1(s)) {
                classes[1] = false;
                break;
            }
        }
        // S
        for (boolean[] s : sys) {
            if (!checkS(s)) {
                classes[2] = false;
                break;
            }
        }
        // M
        for (boolean[] s : sys) {
            if (!checkM(s)) {
                classes[3] = false;
                break;
            }
        }
        // L
        for (boolean[] s : sys) {
            if (!checkL(s)) {
                classes[4] = false;
                break;
            }
        }
        return classes;
    }

    static boolean checkT0(boolean[] vec) {
        // true, если первый набор дает 0
        return !vec[0];
    }

    static boolean checkT1(boolean[] vec) {
        // true, если последний набор дает 1
        return vec[vec.length - 1];
    }

    static boolean checkS(boolean[] vec) {
        // функция самодвойственна, если на противополножных наборах принимает противоположные значения.
        int n = vec.length;
        for (int i = 0; i < n / 2; i++) {
            if (vec[n - i - 1] == vec[i]) {
                return false;
            }
        }
        return true;
    }

    static boolean checkM(boolean[] vec) {
        // сравниваем значения функции на соседних наборах
        int step = vec.length;
        while ((step /= 2) > 0) {
            int i = 0;
            while (i < vec.length) {
                for (int j = 0; j < step; i++, j++) {
                    if (i + step < vec.length && vec[i] && !vec[i + step]) { // vec[i] > vec[i+step]
                        return false;
                    }
                }
                i += step;
            }
        }
        return true;
    }

    static boolean checkL(boolean[] vec) {
        int n = vec.length;
        // создаю треугольную таблицу
        List<boolean[]> table = new ArrayList<>();
        table.add(vec);
        // первая строка - функция, остальное вычисляется вниз по строкам
        for (int i = 1; i < n; i++) {
            table.add(new boolean[n - i]);
            for (int j = 0; j < n - i; j++) {
                table.get(i)[j] = table.get(i - 1)[j] ^ table.get(i - 1)[j + 1]; // берем симметрическую разность
            }
            if (table.get(i)[0]) {
                // проверка на степень двойки (то есть, есть ли в наборе больше одной 1)
                if (!((i & (i - 1)) == 0)) {
                    return false;
                }
            }
        }
        return true;
    }

    static boolean[] randomFunctionVector(int n) {
        Random rand = new Random();
        n = 1 << n;
        boolean[] vec = new boolean[n];
        for (int i = 0; i < n; i++) {
            vec[i] = rand.nextBoolean();
        }
        return vec;
    }
}
