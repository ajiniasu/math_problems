package logicmaster.app;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

public class MainController {

    private boolean isFullscreen = true;

    @FXML
    private Button taskButton;

    @FXML
    void handleTaskButtonClick(ActionEvent event) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("TaskList.fxml"));
            Parent root = fxmlLoader.load();
            Scene scene = new Scene(root);
            Stage stage = (Stage) taskButton.getScene().getWindow();
            TaskListController taskListController = fxmlLoader.getController();
            taskListController.setFullscreen(isFullscreen);
            stage.setScene(scene);
            stage.setFullScreen(isFullscreen);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void handleToggleFullscreen(ActionEvent event) {
        isFullscreen = !isFullscreen;
        Stage stage = (Stage) taskButton.getScene().getWindow();
        stage.setFullScreen(isFullscreen);
    }
}
