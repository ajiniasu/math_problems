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
import java.util.Random;

public class Task10Controller {

    @FXML
    private TextField userAnswerInput;

    @FXML
    private Label functionVectorLabel;

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

    private final String[] prepositionalClasses = {"1", "2", "3", "4", "5"};

    @FXML
    private void initialize() {
        Random rand = new Random();
        int n = rand.nextInt(3) + 2; //кол-во аргументов от 2 до 4 включительно
        boolean[] vec = randomFunctionVector(n);

        StringBuilder functionVectorText = new StringBuilder(" ");
        for (boolean b : vec) {
            functionVectorText.append(b ? 1 : 0);
        }
        functionVectorLabel.setText(functionVectorText.toString());
    }

    @FXML
    private void checkAnswer(ActionEvent event) {
        boolean[] ans = new boolean[5];
        ans[0] = checkT0();
        ans[1] = checkT1();
        ans[2] = checkS();
        ans[3] = checkM();
        ans[4] = checkL();

        boolean[] userAns = new boolean[5];
        String[] tokens = userAnswerInput.getText().split("\\s+");
        if (!tokens[0].equals("-")) {
            for (String token : tokens) {
                int index = Integer.parseInt(token) - 1;
                userAns[index] = true;
            }
        }

        if (Arrays.equals(ans, userAns)) {
            resultLabel.setText("Ответ верен");
        } else {
            StringBuilder correctAns = new StringBuilder("Неверно. Правильный ответ: ");
            int cnt = 0;
            for (int i = 0; i < 5; i++) {
                if (ans[i]) {
                    correctAns.append(prepositionalClasses[i]).append(" ");
                    cnt++;
                }
            }
            if (cnt == 0) {
                correctAns.append("Функция не принадлежит предполным классам.");
            }
            resultLabel.setText(correctAns.toString());
        }
    }

    private boolean checkT0() {
        boolean[] vec = getFunctionVector();
        return !vec[0];
    }

    private boolean checkT1() {
        boolean[] vec = getFunctionVector();
        return vec[vec.length - 1];
    }

    private boolean checkS() {
        boolean[] vec = getFunctionVector();
        int n = vec.length;
        for (int i = 0; i < n / 2; i++) {
            if (vec[n - i - 1] == vec[i]) {
                return false;
            }
        }
        return true;
    }

    private boolean checkM() {
        boolean[] vec = getFunctionVector();
        int step = vec.length;
        while ((step /= 2) > 0) {
            int i = 0;
            while (i < vec.length) {
                for (int j = 0; j < step; i++, j++) {
                    if (vec[i] && !vec[i + step]) {
                        return false;
                    }
                }
                i += step;
            }
        }
        return true;
    }

    private boolean checkL() {
        boolean[] vec = getFunctionVector();
        int n = vec.length;
        boolean[][] table = new boolean[n][];
        table[0] = vec;
        for (int i = 1; i < n; i++) {
            table[i] = new boolean[n - i];
            for (int j = 0; j < n - i; j++) {
                table[i][j] = table[i - 1][j] ^ table[i - 1][j + 1];
            }
        }
        for (int i = 1; i < n; i++) {
            if (table[i][0]) {
                if (!((i & (i - 1)) == 0)) {
                    return false;
                }
            }
        }
        return true;
    }

    private boolean[] getFunctionVector() {
        String[] vecStr = functionVectorLabel.getText().replaceAll("[^01]", "").split("");
        boolean[] vec = new boolean[vecStr.length];
        for (int i = 0; i < vecStr.length; i++) {
            vec[i] = vecStr[i].equals("1");
        }
        return vec;
    }

    private boolean[] randomFunctionVector(int n) {
        Random rand = new Random();
        n = 1 << n;
        boolean[] vec = new boolean[n];
        for (int i = 0; i < n; i++) {
            vec[i] = rand.nextBoolean();
        }
        return vec;
    }
}
