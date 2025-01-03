package controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.event.ActionEvent;
import javafx.stage.Stage;
import javafx.scene.control.TextField;
import Manager.SessionManager;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;

import dao.userMegementDao;
import impl.userMegement;


public class UpdateUserController {
    @FXML
    private Button cancelBtn;
//    @FXML
//    private TextField idField;
    @FXML
    private TextField ageField;
    @FXML
    private TextField genderField;
    @FXML
    private TextField phonenumberField;
    @FXML
    private TextField schoolidField;
    @FXML
    private TextField birthdateField;
    @FXML
    private Button updateButton;

    private userMegementDao userService;

    @FXML
    public void initialize() {
        userService = new userMegement(); // 直接初始化
    }

    // 更新信息
    @FXML
    private void updateUser(ActionEvent event) {
        try {
            int id = SessionManager.getInstance().getCurrentUser().getId();
//            int id = Integer.parseInt(idField.getText());
            int age = Integer.parseInt(ageField.getText());
            String gender = genderField.getText();
            String phonenumber = phonenumberField.getText();
            String schoolid = schoolidField.getText();
            String birthdate = birthdateField.getText();

            userService.update(age, gender, phonenumber, schoolid, birthdate, id);
            showAlert(Alert.AlertType.INFORMATION, "成功", "用户信息已更新");
        } catch (SQLException e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "错误", "无法更新用户信息");
        }
    }

    private void showAlert(Alert.AlertType alertType, String title, String content) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setContentText(content);
        alert.showAndWait();
    }

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
            Stage stage = (Stage) cancelBtn.getScene().getWindow();
            stage.setScene(new Scene(adminView));
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
