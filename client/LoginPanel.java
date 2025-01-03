import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

public class LoginPanel extends Application {

    private static final String ADMIN_USERNAME = "admin";  // 管理员用户名
    private static final String ADMIN_PASSWORD = "admin123";  // 管理员密码

    private boolean isAdminLogin;

    public LoginPanel(boolean isAdminLogin) {
        this.isAdminLogin = isAdminLogin; // 根据传入参数确定是管理员登录还是用户登录
    }

    @Override
    public void start(Stage primaryStage) {
        VBox layout = new VBox(10);
        layout.setPadding(new javafx.geometry.Insets(20));

        // 用户名和密码输入框
        TextField usernameField = new TextField();
        usernameField.setPromptText("Enter Username");

        PasswordField passwordField = new PasswordField();
        passwordField.setPromptText("Enter Password");

        Button loginButton = new Button("Login");

        loginButton.setOnAction(e -> {
            String username = usernameField.getText();
            String password = passwordField.getText();

            if (isAdminLogin) {
                // 管理员登录逻辑
                if (username.equals(ADMIN_USERNAME) && password.equals(ADMIN_PASSWORD)) {
                    // 进入管理员面板
                    new AdminPanel().start(new Stage());
                    primaryStage.close();
                } else {
                    new Alert(Alert.AlertType.ERROR, "Invalid Admin credentials").showAndWait();
                }
            } else {
                // 普通用户登录逻辑
                try {
                    int userId = userService.authenticate(username, password);
                    if (userId != -1) {
                        // 进入用户面板
                        new UserPanel(userService).start(new Stage());
                        primaryStage.close();
                    } else {
                        new Alert(Alert.AlertType.ERROR, "Invalid username or password").showAndWait();
                    }
                } catch (Exception ex) {
                    new Alert(Alert.AlertType.ERROR, "Error logging in: " + ex.getMessage()).showAndWait();
                }
            }
        });

        layout.getChildren().addAll(usernameField, passwordField, loginButton);

        Scene scene = new Scene(layout, 300, 200);
        primaryStage.setScene(scene);
        primaryStage.setTitle(isAdminLogin ? "Admin Login" : "User Login");
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
