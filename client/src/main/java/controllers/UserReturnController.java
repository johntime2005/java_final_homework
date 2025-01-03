package controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.event.ActionEvent;
import javafx.stage.Stage;
import javafx.scene.Node;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import dao.userMegementDao;
import impl.userMegement;
import model.User;
import dao.bookMegementDao;
import impl.bookMegement;
import model.Book;

import Manager.SessionManager;
import utils.ConnectToServer;

public class UserReturnController {
    private ConnectToServer server = new ConnectToServer();

    @FXML
    private PasswordField bookIdField;

    @FXML
    private Button returnButton;

    @FXML
    private Button cancelButton;

    private userMegementDao userService;
    private bookMegementDao bookService;

    @FXML
    private void initialize() {
        userService = new userMegement(); // 直接初始化
        bookService = new bookMegement();
    }

    // 借书：用户输入用户名和书籍编号，点击“借书”按钮后，调用将该书籍借给该用户
    @FXML
    private void returnBook(ActionEvent event) {
        User currentUser = SessionManager.getInstance().getCurrentUser();
        if (currentUser == null) {
            showAlert(Alert.AlertType.ERROR, "错误", "请先登录");
            return;
        }
        try {
            int bookId = Integer.parseInt(bookIdField.getText());
            userService.returnBook(currentUser.getId(), bookId);
            int newBalance = userService.getUserBalance(currentUser.getId());
            showAlert(Alert.AlertType.INFORMATION, "成功", 
                String.format("还书成功，当前余额：%d", newBalance));
        } catch (NumberFormatException e) {
            showAlert(Alert.AlertType.ERROR, "错误", "请输入有效的图书ID");
        } catch (SQLException e) {
            if (e.getMessage().contains("只有借书人")) {
                showAlert(Alert.AlertType.ERROR, "错误", "只有借书人才能还书");
            } else if (e.getMessage().contains("这本书不是您借的")) {
                showAlert(Alert.AlertType.ERROR, "错误", "这本书不是您借的，无法归还。");
            } else {
                showAlert(Alert.AlertType.ERROR, "错误", e.getMessage());
            }
        }
    }

    @FXML
    private void loadFXML(String fxmlPath, String title, ActionEvent event) {
        try {
            URL url = getClass().getResource(fxmlPath);
            if (url == null) {
                throw new RuntimeException("无法找到FXML文件: " + fxmlPath);
            }
            Parent root = FXMLLoader.load(url);
            Stage stage = new Stage();
            stage.setTitle(title);
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "错误", "无法加载新窗口");
        }
    }

    @FXML
    private void cancel(ActionEvent event) {
        try {
            Parent adminView = FXMLLoader.load(getClass().getResource("/views/user_main.fxml"));
            Stage stage = (Stage) cancelButton.getScene().getWindow();
            stage.setScene(new Scene(adminView));
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void showAlert(Alert.AlertType alertType, String title, String content) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setContentText(content);
        alert.showAndWait();
    }
}
