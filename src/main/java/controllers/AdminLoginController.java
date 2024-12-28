package controllers;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.control.PasswordField;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.Parent;

public class AdminLoginController {
    @FXML
    private TextField usernameField;
    
    @FXML
    private PasswordField passwordField;

    @FXML
    private void handleLogin() {
        String username = usernameField.getText();
        String password = passwordField.getText();
        
        // 这里添加管理员验证逻辑
        if ("admin".equals(username) && "admin".equals(password)) {
            try {
                // 登录成功，跳转到管理员主界面
                Stage stage = (Stage) usernameField.getScene().getWindow();
                Parent root = FXMLLoader.load(getClass().getResource("/views/adminsystem.fxml"));
                stage.setScene(new Scene(root));
            } catch (Exception e) {
                showError("无法加载管理员界面", e.getMessage());
            }
        } else {
            showError("登录失败", "用户名或密码错误！");
        }
    }

    @FXML
    private void handleBack() {
        try {
            Stage stage = (Stage) usernameField.getScene().getWindow();
            Parent root = FXMLLoader.load(getClass().getResource("/views/login.fxml"));
            stage.setScene(new Scene(root));
        } catch (Exception e) {
            showError("返回失败", e.getMessage());
        }
    }

    private void showError(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setContentText(content);
        alert.showAndWait();
    }
}
