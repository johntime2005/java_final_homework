//package controllers;
//
//import dao.bookMegementDao;
//import dao.userMegementDao;
//import impl.bookMegement;
//import impl.userMegement;
//import javafx.event.ActionEvent;
//import javafx.fxml.FXML;
//import javafx.fxml.FXMLLoader;
//import javafx.scene.Parent;
//import javafx.scene.Scene;
//import javafx.scene.control.Alert;
//import javafx.scene.control.Button;
//import javafx.scene.control.TextField;
//import javafx.stage.Stage;
//import model.User;
//import utils.DatabaseConnection;
//
//import java.io.IOException;
//import java.net.URL;
//import java.sql.Connection;
//import java.sql.SQLException;
//
//public class UpdateuserController {
//    @FXML
//    private Button cancelBtn;
//    @FXML
//    private PasswordField id;
//    @FXML
//    private PasswordField age;
//    @FXML
//    private PasswordField gender;
//    @FXML
//    private PasswordField phonenumber;
//    @FXML
//    private PasswordField schoolid;
//    @FXML
//    private PasswordField birthdate;
//    @FXML
//    private Button updateButton;
//
//    private Connection connection;
//    private userMegementDao userService;
//
//    private void initialize() {
//        try {
//            connection = DatabaseConnection.getConnection();
//            userService = new userMegement(connection);
//        } catch (SQLException e) {
//            e.printStackTrace();
//            showAlert(Alert.AlertType.ERROR, "错误", "无法连接到数据库");
//        }
//    }
//    //更新信息
//    private void updateUser() {
//        try {
//
//            userService.update(user);
//            showAlert(Alert.AlertType.INFORMATION, "成功", "用户信息已更新");
//        } catch (SQLException e) {
//            e.printStackTrace();
//            showAlert(Alert.AlertType.ERROR, "错误", "无法更新用户信息");
//        }
//    }
//    private void showAlert(Alert.AlertType alertType, String title, String content) {
//        Alert alert = new Alert(alertType);
//        alert.setTitle(title);
//        alert.setContentText(content);
//        alert.showAndWait();
//    }
//
//    private void loadFXML(String fxmlPath, String title, ActionEvent event) {
//        try {
//            URL url = getClass().getResource(fxmlPath);
//            if (url == null) {
//                throw new RuntimeException("无法找到FXML文件: " + fxmlPath);
//            }
//            Parent root = FXMLLoader.load(url);
//            Stage stage = new Stage();
//            stage.setTitle(title);
//            stage.setScene(new Scene(root));
//            stage.show();
//        } catch (IOException e) {
//            e.printStackTrace();
//            showAlert(Alert.AlertType.ERROR, "错误", "无法加载新窗口");
//        }
//    }
//    @FXML
//    private void cancel(ActionEvent event) {
//        try {
//            Parent adminView = FXMLLoader.load(getClass().getResource("/views/user_main.fxml"));
//            Stage stage = (Stage) cancelBtn.getScene().getWindow();
//            stage.setScene(new Scene(adminView));
//            stage.show();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//}
