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
import utils.DatabaseConnection;

public class UserMenuController {
    @FXML
    private Button browseBookBtn; // 修正 ID
    @FXML
    private Button borrowBookBtn; // 修正 ID
    @FXML
    private Button returnBookBtn; // 修正 ID
    @FXML
    private Button modifyBtn; // 修正 ID
    @FXML
    private Button cancelBtn; // 修正 ID

     @FXML
    private void initialize() {
         try {
             Connection connection = DatabaseConnection.getConnection();
//             userService = new userMegement(connection);
         } catch (SQLException e) {
             e.printStackTrace();
             showAlert(Alert.AlertType.ERROR, "错误", "无法连接到数据库");
         }
    }
    //浏览图书
    @FXML
    private void browseBook(ActionEvent event) {
        loadFXML("/views/userquery.fxml", "浏览图书", event);
    }
    //借书
    @FXML
    private void borrowBook(ActionEvent event) {
        loadFXML("/views/borrowbook.fxml", "借书", event);
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
