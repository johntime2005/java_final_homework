package controllers;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class AdminLoginController {

    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private void handleLogin() {
        String username = usernameField.getText();
        String password = passwordField.getText();

        // 这里可以添加登录逻辑
        if ("admin".equals(username) && "password".equals(password)) {
            showAlert(AlertType.INFORMATION, "登录成功");
        } else {
            showAlert(AlertType.ERROR, "用户名或密码错误");
        }
    }

    @FXML
    private void handleBack() {
        // 返回逻辑
    }

    private void showAlert(AlertType alertType, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle("消息");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
