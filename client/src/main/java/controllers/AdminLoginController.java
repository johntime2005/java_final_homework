package controllers;
import dao.userMegementDao;
import impl.userMegement;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;
import model.User;

import utils.userService;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
import utils.BookService;

public class AdminLoginController {
    private userService userService;

    public AdminLoginController() {
        BookService bookService = new BookService();
        this.userService = new userService(new userMegement(), bookService);
    }

    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private void handleLogin(ActionEvent event) {
        String username = usernameField.getText();
        String password = passwordField.getText();

        if (username.isEmpty() || password.isEmpty()) {
            showAlert1(Alert.AlertType.WARNING, "警告", "用户名和密码不能为空");
            return;
        }

        try {
            User user = userService.login(username, password);
            if (user != null && "admin".equals(user.getUserType())) {
                loadFXML("/views/adminsystem.fxml", "管理员主界面", event);
            } else {
                showAlert1(Alert.AlertType.ERROR, "错误", "用户名或密码错误，或当前账号非管理员账号");
            }
        } catch (SQLException e) {
            showAlert1(Alert.AlertType.ERROR, "错误", "数据库错误: " + e.getMessage());
            System.out.println("数据库错误: " + e.getMessage());
        } catch (Exception e) {
            showAlert1(Alert.AlertType.ERROR, "错误", "登录失败: " + e.getMessage());
            System.out.println("登录失败: " + e.getMessage());
        }
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
            showAlert1(Alert.AlertType.ERROR, "错误", "加载界面失败: " + e.getMessage());
        }
    }
    @FXML

    private void handleBack(ActionEvent event) {
        try {
            Parent adminView = FXMLLoader.load(getClass().getResource("/views/login.fxml"));
            Stage stage = (Stage) usernameField.getScene().getWindow();
            stage.setScene(new Scene(adminView));
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
            showAlert(AlertType.ERROR, "返回登录界面失败: " + e.getMessage());
        }
    }

    private void showAlert1(Alert.AlertType alertType, String title, String content) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setContentText(content);
        alert.showAndWait();
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
