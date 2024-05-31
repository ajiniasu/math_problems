package logicmaster.app;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

public class TaskListController {

    @FXML
    private Button backButton;

    private boolean isFullscreen = false;

    public void setFullscreen(boolean fullscreen) {
        isFullscreen = fullscreen;
    }

    @FXML
    private void handleBackButtonClick(ActionEvent event) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("main.fxml"));
            Parent root = fxmlLoader.load();
            Scene scene = new Scene(root);
            Stage stage = (Stage) backButton.getScene().getWindow();
            stage.setScene(scene);
            stage.setFullScreen(isFullscreen);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleToggleFullscreen(ActionEvent event) {
        Stage stage = (Stage) backButton.getScene().getWindow();
        isFullscreen = !isFullscreen;
        stage.setFullScreen(isFullscreen);
    }

    private void openTask(String resourcePath, String title) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(resourcePath));
            Parent root = fxmlLoader.load();
            Scene scene = new Scene(root);
            Stage stage = (Stage) backButton.getScene().getWindow();
            stage.setScene(scene);
            stage.setTitle(title);
            stage.setFullScreen(isFullscreen);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleTask1ButtonClick(ActionEvent event) {
        openTask("/logicmaster/app/TaskList/task1.fxml", "Задача 1");
    }

    @FXML
    private void handleTask2ButtonClick(ActionEvent event) {
        openTask("/logicmaster/app/TaskList/task2.fxml", "Задача 2");
    }

    @FXML
    private void handleTask3ButtonClick(ActionEvent event) {
        openTask("/logicmaster/app/TaskList/task3.fxml", "Задача 3");
    }

    @FXML
    private void handleTask4ButtonClick(ActionEvent event) {
        openTask("/logicmaster/app/TaskList/task4.fxml", "Задача 4");
    }

    @FXML
    private void handleTask5ButtonClick(ActionEvent event) {
        openTask("/logicmaster/app/TaskList/task5.fxml", "Задача 5");
    }

    @FXML
    private void handleTask6ButtonClick(ActionEvent event) {
        openTask("/logicmaster/app/TaskList/task6.fxml", "Задача 6");
    }

    @FXML
    private void handleTask7ButtonClick(ActionEvent event) {
        openTask("/logicmaster/app/TaskList/task7.fxml", "Задача 7");
    }

    @FXML
    private void handleTask8ButtonClick(ActionEvent event) {
        openTask("/logicmaster/app/TaskList/task8.fxml", "Задача 8");
    }

    @FXML
    private void handleTask9ButtonClick(ActionEvent event) {
        openTask("/logicmaster/app/TaskList/task9.fxml", "Задача 9");
    }

    @FXML
    private void handleTask10ButtonClick(ActionEvent event) {
        openTask("/logicmaster/app/TaskList/task10.fxml", "Задача 10");
    }

    @FXML
    private void handleTask11ButtonClick(ActionEvent event) {
        openTask("/logicmaster/app/TaskList/task11.fxml", "Задача 11");
    }

    @FXML
    private void handleTask12ButtonClick(ActionEvent event) {
        openTask("/logicmaster/app/TaskList/task12.fxml", "Задача 12");
    }
    public void setIsFullscreen(boolean isFullscreen) {
        this.isFullscreen = isFullscreen;
    }
}
