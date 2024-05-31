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
import java.util.ArrayList;
import java.util.List;

public class Task9Controller {

    @FXML
    private TextField numVariablesInput;

    @FXML
    private TextField functionVectorInput;

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

    private List<String> pcnf = new ArrayList<>();

    // Метод для вычисления таблицы истинности по вектору функции
    private boolean[][] computeTruthTable(int[] functionVector, int numVariables) {
        int numRows = (int) Math.pow(2, numVariables);
        boolean[][] truthTable = new boolean[numRows][numVariables + 1]; // +1 для столбца результатов

        for (int i = 0; i < numRows; i++) {
            String binaryString = Integer.toBinaryString(i);
            while (binaryString.length() < numVariables) {
                binaryString = "0" + binaryString;
            }
            for (int j = 0; j < numVariables; j++) {
                truthTable[i][j] = binaryString.charAt(j) == '1';
            }
            truthTable[i][numVariables] = functionVector[i] == 1;
        }

        return truthTable;
    }

    // Метод для построения СКНФ из таблицы истинности
    private List<String> buildPCNF(boolean[][] truthTable) {
        List<String> pcnf = new ArrayList<>();
        int numVariables = truthTable[0].length - 1; // учитываем последний столбец с результатами функции

        for (int i = 0; i < truthTable.length; i++) {
            if (!truthTable[i][numVariables]) { // проверяем последний столбец на ложь
                StringBuilder term = new StringBuilder("(");
                for (int j = 0; j < numVariables; j++) {
                    if (!truthTable[i][j]) { // переменная с отрицанием
                        term.append((char) ('A' + j)); // используем A, B, C... для переменных
                    } else {
                        term.append((char) ('A' + j)).append("'"); // используем A', B', C'... для отрицания переменных
                    }
                    term.append(" AND ");
                }
                term.setLength(term.length() - 5); // Убираем последний " AND "
                term.append(") OR ");
                pcnf.add(term.toString());
            }
        }
        if (!pcnf.isEmpty()) {
            pcnf.set(pcnf.size() - 1, pcnf.get(pcnf.size() - 1).replace(" OR ", "")); // Убираем последний " OR "
        }
        return pcnf;
    }

    @FXML
    private void generateTruthTable(ActionEvent event) {
        int numVariables = Integer.parseInt(numVariablesInput.getText());
        String[] vectorStrings = functionVectorInput.getText().split("\\s+");
        int[] functionVector = new int[vectorStrings.length];
        for (int i = 0; i < vectorStrings.length; i++) {
            functionVector[i] = Integer.parseInt(vectorStrings[i]);
        }

        // Рассчитать таблицу истинности
        boolean[][] truthTable = computeTruthTable(functionVector, numVariables);

        // Отобразить таблицу истинности
        StringBuilder tableText = new StringBuilder();
        for (boolean[] row : truthTable) {
            for (boolean value : row) {
                tableText.append(value ? "1" : "0").append(" ");
            }
            tableText.append("\n");
        }
        functionVectorLabel.setText(tableText.toString());
    }

    @FXML
    private void displayPCNF(ActionEvent event) {
        // Построить СКНФ
        int numVariables = Integer.parseInt(numVariablesInput.getText());
        String[] vectorStrings = functionVectorInput.getText().split("\\s+");
        int[] functionVector = new int[vectorStrings.length];
        for (int i = 0; i < vectorStrings.length; i++) {
            functionVector[i] = Integer.parseInt(vectorStrings[i]);
        }
        boolean[][] truthTable = computeTruthTable(functionVector, numVariables);
        pcnf = buildPCNF(truthTable);

        // Отобразить СКНФ
        StringBuilder pcnfText = new StringBuilder();
        for (String term : pcnf) {
            pcnfText.append(term);
        }
        resultLabel.setText(pcnfText.toString());
    }
}
