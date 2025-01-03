package controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.event.ActionEvent;
import javafx.stage.Stage;
import javafx.scene.Node;

import Manager.SessionManager;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import dao.userMegementDao;
import impl.userMegement;
import model.User;


public class UserLoginController {
    @FXML
    private TextField usernameField;
    
    @FXML
    private PasswordField passwordField;

    private userMegementDao userService;

    @FXML
    private void initialize() {
        userService = new userMegement(); // 直接初始化
    }
    
    @FXML
    private void handleLogin(ActionEvent event) {
        String username = usernameField.getText();
        String password = passwordField.getText();

        if (username.isEmpty() || password.isEmpty()) {
            showAlert(Alert.AlertType.WARNING, "警告", "用户名和密码不能为空");
            return;
        }

        try {
            User user = userService.login(username, password);
            if (user != null && "user".equals(user.getUserType())) {
                SessionManager.getInstance().setCurrentUser(user);
                loadFXML("/views/user_main.fxml", "用户主界面", event);
            } else {
                showAlert(Alert.AlertType.ERROR, "错误", "用户名或密码错误，或当前账号非普通用户账号");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "错误", "登录失败: " + e.getMessage());
        }
    }
    
    @FXML
    private void handleRegister(ActionEvent event) {
        loadFXML("/views/UserRegister.fxml", "用户注册", event);
    }
    
    @FXML
    private void handleBack(ActionEvent event) {
        loadFXML("/views/login.fxml", "选择登录类型", event);
    }

    private void loadFXML(String fxmlPath, String title, ActionEvent event) {
        try {
            URL url = getClass().getResource(fxmlPath);
            if (url == null) {
                throw new RuntimeException("无法找到FXML文件: " + fxmlPath);
            }
            Parent root = FXMLLoader.load(url);
            Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle(title);
        } catch (IOException e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "错误", "加载界面失败: " + e.getMessage());
        }
    }

    private void showAlert(Alert.AlertType alertType, String title, String content) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setContentText(content);
        alert.showAndWait();
    }
}
