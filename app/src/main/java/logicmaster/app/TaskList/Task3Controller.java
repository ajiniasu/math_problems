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

public class Task3Controller {

    @FXML
    private TextField residual0Input;

    @FXML
    private TextField residual1Input;

    @FXML
    private TextField argIndexInput;

    @FXML
    private Label resultLabel; // Изменение TextArea на Label

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
    private void handleComputeVector(ActionEvent event) {
        // Получаем введенные пользователем значения
        String res0 = residual0Input.getText();
        String res1 = residual1Input.getText();
        int narg = Integer.parseInt(argIndexInput.getText());

        // Вычисляем вектор функции
        String vector = vecFunction(narg, res0, res1);

        // Выводим результат в Label
        resultLabel.setText(vector);
    }

    // Метод для вычисления вектора функции
    private String vecFunction(int narg, String residual0, String residual1) {
        int div = 1 << (narg - 1); // На сколько частей нужно разделить каждую остаточную
        int step = residual0.length() / div; // Шаг цикла

        // Собираем вектор
        StringBuilder vector = new StringBuilder();
        for (int i = 0; i < residual0.length(); i += step) {
            vector.append(residual0, i, i + step);
            vector.append(residual1, i, i + step);
        }
        return vector.toString();
    }
}
