module logicmaster.app {
    requires javafx.controls;
    requires javafx.fxml;

    opens logicmaster.app to javafx.fxml;
    opens logicmaster.app.TaskList to javafx.fxml;

    exports logicmaster.app;
}
