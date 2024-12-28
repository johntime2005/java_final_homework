package controllers;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;

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
            switchToAdminManu();
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
    private void switchToAdminManu() {// 切换到管理员菜单界面的逻辑
        URL url = getClass().getResource("/views/adminsystem.fxml");
        if (url == null) {
            showAlert(AlertType.ERROR, "找不到管理员菜单界面文件");
            return;
        }
        try {
            Parent adminManu = FXMLLoader.load(url);
            Stage stage = (Stage) usernameField.getScene().getWindow();
            stage.setScene(new Scene(adminManu));
            stage.setTitle("管理员菜单");
        } catch (IOException e) {
            e.printStackTrace();
            showAlert(AlertType.ERROR, "加载管理员菜单界面失败: " + e.getMessage());
        }

    }
}
