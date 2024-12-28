package controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.event.ActionEvent;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;

public class LoginController {
    @FXML
    private Button adminLoginBtn;
    
    @FXML
    private Button userLoginBtn;
    
    @FXML
    private void handleAdminLogin(ActionEvent event) {
        URL url = getClass().getResource("/views/admin_login.fxml");
        if (url == null) {
            showError("错误", "找不到管理员登录界面文件");
            return;
        }
        try {
            Parent adminLogin = FXMLLoader.load(url);
            Stage stage = (Stage) adminLoginBtn.getScene().getWindow();
            stage.setScene(new Scene(adminLogin));
            stage.setTitle("管理员登录");
        } catch (IOException e) {
            e.printStackTrace();
            showError("错误", "加载管理员登录界面失败: " + e.getMessage());
        }
    }
    
    @FXML
    private void handleUserLogin(ActionEvent event) {
        URL url = getClass().getResource("/views/user_login.fxml");
        if (url == null) {
            showError("错误", "找不到用户登录界面文件");
            return;
        }
        try {
            Parent userLogin = FXMLLoader.load(url);
            Stage stage = (Stage) userLoginBtn.getScene().getWindow();
            stage.setScene(new Scene(userLogin));
            stage.setTitle("用户登录");
        } catch (IOException e) {
            e.printStackTrace();
            showError("错误", "加载用户登录界面失败: " + e.getMessage());
        }
    }

    private void showError(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setContentText(content);
        alert.showAndWait();
    }
}
