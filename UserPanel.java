import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.sql.SQLException;
import java.util.List;

public class UserPanel extends Application {
    private userService userService;

    @Override
    public void start(Stage primaryStage) {
        // Layout setup for user panel
        VBox layout = new VBox(10);

        TextField userIdField = new TextField();
        userIdField.setPromptText("Enter user ID");
        Button queryButton = new Button("Query User");

        queryButton.setOnAction(e -> {
            try {
                List<User> users = userService.getUserById(Integer.parseInt(userIdField.getText()));
                if (users.isEmpty()) {
                    new Alert(Alert.AlertType.INFORMATION, "User not found!").showAndWait();
                } else {
                    // Display user information
                    users.forEach(user -> {
                        System.out.println(user);
                    });
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        });

        layout.getChildren().addAll(userIdField, queryButton);

        Scene scene = new Scene(layout, 400, 300);
        primaryStage.setScene(scene);
        primaryStage.setTitle("图书管理员");
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
