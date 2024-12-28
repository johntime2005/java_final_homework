package controllers;

import impl.userMegement;
import model.User;
import utils.DatabaseConnection;  // 假设你有这个类来处理数据库连接
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.Parent;
import java.sql.Connection;

public class AdminLoginController {
    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    private userMegement userManager;
    private Connection connection;

    public AdminLoginController() {
        try {
            connection = DatabaseConnection.getConnection();  // 获取数据库连接
            userManager = new userMegement(connection);
        } catch (Exception e) {
            showError("数据库连接错误", "无法连接到数据库：" + e.getMessage());
        }
    }

    @FXML
    private void handleLogin() {
        String username = usernameField.getText();
        String password = passwordField.getText();
        
        try {
            User user = userManager.login(username, password);
            if (user != null && "admin".equals(user.getUserType())) {
                // 登录成功，跳转到管理员主界面
                Stage stage = (Stage) usernameField.getScene().getWindow();
                Parent root = FXMLLoader.load(getClass().getResource("/views/adminsystem.fxml"));
                stage.setScene(new Scene(root));
            } else {
                showError("登录失败", "用户名或密码错误，或者该用户不是管理员！");
            }
        } catch (Exception e) {
            showError("登录错误", "登录过程中发生错误：" + e.getMessage());
        }
    }

    @FXML
    private void handleLogin() {
        try {
            User user = userService.login(usernameField.getText(), passwordField.getText());
            if (user != null && "admin".equals(user.getUserType())) {
                adminPanel.setLoggedInUser(user);
                adminPanel.showAdminLoginInterface(stage);
            } else {
                new Alert(AlertType.ERROR, "管理员用户名或密码错误！").showAndWait();
            }
        } catch (SQLException ex) {
            new Alert(AlertType.ERROR, "登录失败：" + ex.getMessage()).showAndWait();
        }
    }
    @FXML
    private void handleBack() {
        adminPanel.showRoleSelectionInterface(stage);
    }
}
