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

public class Task6Controller {

    @FXML
    private Label functionVectorLabel;

    @FXML
    private TextField dnfInput;

    @FXML
    private Label resultLabel;

    @FXML
    private Button resetButton;

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

    private List<int[]> functionVectors = new ArrayList<>();
    private List<String> dnfs = new ArrayList<>();
    private Random random = new Random();
    private int[] randomFunctionVector;

    public void initialize() {
        // Добавление векторов функций и соответствующих ДНФ в список
        functionVectors.add(new int[]{1, 1, 1, 1});
        dnfs.add("(A || B) && (A || !B) && (!A || B) && (!A || !B)");

        functionVectors.add(new int[]{1, 1, 1, 0});
        dnfs.add("(A || B) && (A || !B) && (!A || B)");

        functionVectors.add(new int[]{1, 1, 0, 1});
        dnfs.add("(A || B) && (A || !B) && (!A || !B)");

        functionVectors.add(new int[]{1, 0, 1, 1});
        dnfs.add("(A || B) && (!A || B) && (!A || !B)");

        functionVectors.add(new int[]{0, 1, 1, 1});
        dnfs.add("(A || B) && (A || !B) && (!A || B)");

        functionVectors.add(new int[]{0, 0, 1, 1});
        dnfs.add("(A || B) && (!A || B)");

        functionVectors.add(new int[]{1, 0, 0, 1});
        dnfs.add("(A || B) && (!A || !B)");

        functionVectors.add(new int[]{1, 1, 0, 0});
        dnfs.add("(A || B) && (A || !B)");

        functionVectors.add(new int[]{0, 1, 0, 1});
        dnfs.add("(A || B) && (!A || B)");

        functionVectors.add(new int[]{1, 0, 1, 0});
        dnfs.add("(A || B) && (!A || !B)");

        // Выбор случайного вектора функции из всех возможных
        int index = random.nextInt(functionVectors.size());
        randomFunctionVector = functionVectors.get(index);

        // Отображаем информацию в окне
        functionVectorLabel.setText("Вектор функции: " + Arrays.toString(randomFunctionVector).replaceAll("[\\[\\],]", ""));
        resultLabel.setText("Попробуйте угадать ДНФ для данного вектора функции.");
    }

    @FXML
    private void checkDNF(ActionEvent event) {
        String userDNF = dnfInput.getText().trim();

        // Получаем правильную ДНФ из списка на основе случайного выбранного вектора функции
        String correctDNF = dnfs.get(functionVectors.indexOf(randomFunctionVector));

        // Проверка угаданной ДНФ
        if (userDNF.equals(correctDNF)) {
            resultLabel.setText("Правильно!");
        } else {
            resultLabel.setText("Неправильно!");
        }

        // Деактивируем текстовое поле и кнопку "Проверить ДНФ"
        dnfInput.setDisable(true);
        resetButton.setDisable(false);
    }

    @FXML
    private void reset(ActionEvent event) {
        // Очищаем поле ввода и метку результатов
        dnfInput.clear();
        resultLabel.setText("");
        // Активируем текстовое поле и кнопку "Проверить ДНФ"
        dnfInput.setDisable(false);
        resetButton.setDisable(true);
    }
}
