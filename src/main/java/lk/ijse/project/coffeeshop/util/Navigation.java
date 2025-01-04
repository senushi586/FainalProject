package lk.ijse.project.coffeeshop.util;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

public class Navigation {
    private static Parent rootNode;
    private static Scene scene;
    private static Stage stage;

    public static void switchNavigation(String path, ActionEvent event) throws IOException {
        rootNode = FXMLLoader.load(Navigation.class.getResource("/view/" + path));

        stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(rootNode);
        stage.setScene(scene);
        stage.centerOnScreen();
        stage.setResizable(false);
        stage.show();
    }
    public static void changeStage(String fxml, String title) {
        FXMLLoader fxmlLoader = new FXMLLoader(Navigation.class.getResource(String.valueOf(fxml)));
        Parent root1 = null;
        try {
            root1 = fxmlLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Stage stage = new Stage();
        stage.setTitle(title);
        stage.setScene(new Scene(root1));
        stage.setResizable(false);
        stage.show();
    }

    public static void changeScene(String fxml, AnchorPane pane) {
        Parent load = null;
        try {
            load = FXMLLoader.load(Navigation.class.getResource(String.valueOf(fxml)));
        } catch (IOException e) {
            e.printStackTrace();
        }
        pane.getChildren().clear();
        pane.getChildren().add(load);

    }
}
