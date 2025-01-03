import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.SQLException;

public class RegisterPanel extends Application {

    private userService userService;

    @Override
    public void start(Stage primaryStage) {
        try {
            // 假设已经建立了数据库连接
            Connection connection = DatabaseConnection.getConnection();
            BookService bookService = new BookService(new bookMegement(connection));
            userService = new userService(new userMegement(connection), bookService);

            VBox layout = new VBox(10);
            layout.setPadding(new javafx.geometry.Insets(20));

            // 用户名、密码和其他信息输入框
            TextField usernameField = new TextField();
            usernameField.setPromptText("Enter Username");

            PasswordField passwordField = new PasswordField();
            passwordField.setPromptText("Enter Password");

            TextField nameField = new TextField();
            nameField.setPromptText("Enter Name");

            TextField ageField = new TextField();
            ageField.setPromptText("Enter Age");

            TextField balanceField = new TextField();
            balanceField.setPromptText("Enter Balance");

            Button registerButton = new Button("Register");

            // 注册按钮的事件
            registerButton.setOnAction(e -> {
                try {
                    String username = usernameField.getText();
                    String password = passwordField.getText();
                    String name = nameField.getText();
                    int age = Integer.parseInt(ageField.getText());
                    int balance = Integer.parseInt(balanceField.getText());

                    // 创建用户对象并保存
                    User newUser = new User(username, password, name, age, balance);
                    userService.create(newUser);

                    new Alert(Alert.AlertType.INFORMATION, "Account created successfully!").showAndWait();
                    // 注册完成后跳回登录界面
                    new MainPanel().start(new Stage());
                    primaryStage.close();
                } catch (SQLException ex) {
                    new Alert(Alert.AlertType.ERROR, "Error creating account: " + ex.getMessage()).showAndWait();
                } catch (NumberFormatException ex) {
                    new Alert(Alert.AlertType.ERROR, "Invalid input! Please enter valid numbers.").showAndWait();
                }
            });

            layout.getChildren().addAll(
                    usernameField, passwordField, nameField, ageField, balanceField, registerButton
            );

            Scene scene = new Scene(layout, 300, 300);
            primaryStage.setScene(scene);
            primaryStage.setTitle("Register");
            primaryStage.show();
        } catch (SQLException ex) {
            new Alert(Alert.AlertType.ERROR, "Failed to connect to database!").showAndWait();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
