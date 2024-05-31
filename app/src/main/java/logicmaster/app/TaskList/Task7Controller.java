package logicmaster.app.TaskList;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.*;

public class Task7Controller {

    @FXML
    private Label functionVectorLabel;

    @FXML
    private TextField cnfInput;

    @FXML
    private Label resultLabel;

    @FXML
    private Button resetButton;

    private List<int[]> functionVectors = new ArrayList<>();
    private List<String> cnfs = new ArrayList<>();
    private Random random = new Random();
    private int[] randomFunctionVector;

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

    public void initialize() {
        // Добавление векторов функций и соответствующих КНФ в список
        functionVectors.add(new int[]{0, 0, 0, 0});
        cnfs.add("(!A || !B) && (A || !B) && (!A || B) && (A || B)");

        functionVectors.add(new int[]{0, 0, 0, 1});
        cnfs.add("(!A || !B) && (A || !B) && (!A || B)");

        functionVectors.add(new int[]{0, 0, 1, 1});
        cnfs.add("(A || B) && (A || !B)");

        functionVectors.add(new int[]{0, 1, 1, 1});
        cnfs.add("(!A || !B)");

        functionVectors.add(new int[]{0, 1, 0, 1});
        cnfs.add("(!A || !B) && (!A || B)");

        functionVectors.add(new int[]{1, 1, 0, 1});
        cnfs.add("(!A || B)");

        functionVectors.add(new int[]{1, 0, 0, 1});
        cnfs.add("(A || !B) && (!A || B)");

        functionVectors.add(new int[]{1, 0, 0, 0});
        cnfs.add("(A || !B) && (!A || B) && (!A || !B)");

        functionVectors.add(new int[]{1, 1, 0, 0});
        cnfs.add("(!A || B) && (!A || !B)");

        functionVectors.add(new int[]{1, 1, 1, 0});
        cnfs.add("(!A || !B)");

        functionVectors.add(new int[]{1, 0, 1, 0});
        cnfs.add("(A || !B) && (!A || !B)");

        functionVectors.add(new int[]{0, 1, 0, 0});
        cnfs.add("(A || B) && (!A || B) && (!A || !B)");

        functionVectors.add(new int[]{0, 1, 1, 0});
        cnfs.add("(A || B) && (!A || !B)");

        functionVectors.add(new int[]{0, 0, 1, 0});
        cnfs.add("(A || B) && (A || !B) && (!A || !B)");

        // Выбор случайного вектора функции из всех возможных
        int index = random.nextInt(functionVectors.size());
        randomFunctionVector = functionVectors.get(index);

        // Отображаем информацию в окне
        functionVectorLabel.setText("Вектор функции: " + Arrays.toString(randomFunctionVector).replaceAll("[\\[\\],]", ""));
        resultLabel.setText("Попробуйте угадать КНФ для данного вектора функции.");
    }

    @FXML
    private void checkCNF(ActionEvent event) {
        String userCNF = cnfInput.getText().trim();

        // Получаем правильную КНФ из списка на основе случайного выбранного вектора функции
        String correctCNF = cnfs.get(functionVectors.indexOf(randomFunctionVector));

        // Проверка угаданной КНФ
        if (userCNF.equals(correctCNF)) {
            resultLabel.setText("Верно! Ваша КНФ совпадает с сгенерированной.");
        } else {
            resultLabel.setText("Неверно! Ваша КНФ не совпадает с сгенерированной.");
            resultLabel.setText("Верная КНФ: " + correctCNF);
        }

        // Деактивируем текстовое поле и кнопку "Проверить КНФ"
        cnfInput.setDisable(true);
        resetButton.setDisable(false);
    }

    @FXML
    private void reset(ActionEvent event) {
        // Очищаем поле ввода и метку результатов
        cnfInput.clear();
        resultLabel.setText("");
        // Активируем текстовое поле и кнопку "Проверить КНФ"
        cnfInput.setDisable(false);
        resetButton.setDisable(true);
    }
}
