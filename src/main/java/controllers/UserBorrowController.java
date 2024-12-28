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
import utils.DatabaseConnection;

public class UserBorrowController {
    @FXML
    private TextField usernameField;

    @FXML
    private TextField bookIdField;

    private Connection connection;
    private userMegementDao userService;
    private bookMegementDao bookService;

    @FXML
    private void initialize() {
        try {
            connection = DatabaseConnection.getConnection();
            userService = new userMegement(connection);
            bookService = new bookMegement(connection);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    } catch (SQLException e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "错误", "无法连接到数据库");
        }
    }

    // 借书：要求用户输入本人id，图书id，先通过用户id判断这个用户余额是否大于0，再通过图书id判断图书是否被借走。都满足情况才能借书，然后用户余额-1，更改图书被借状态，输出借书成功
    @FXML
    private void borrowBook(ActionEvent event) {
        try {
            int userId = Integer.parseInt(usernameField.getText());
            int bookId = Integer.parseInt(bookIdField.getText());


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
        loadFXML("/views/user_main.fxml", "返回", event);
    }

    @FXML
    private void showAlert(Alert.AlertType alertType, String title, String content) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setContentText(content);
        alert.showAndWait();
    }
}
