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
import Manager.SessionManager;
public class UserMenuController {
    @FXML
    private Button browseBookBtn; 
    @FXML
    private Button borrowBookBtn; 
    @FXML
    private Button returnBookBtn; 
    @FXML
    private Button modifyBtn; 
    @FXML
    private Button cancelBtn; 
    @FXML
    private Button queryBanlanceBtn;

    private userMegementDao userService;

    @FXML
    private void initialize() {
        userService = new userMegement(); // 直接初始化
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
    //还书
    @FXML
    private void returnBook(ActionEvent event) {
        loadFXML("/views/returnbook.fxml", "还书", event);
    }
    //返回
    @FXML
    private void cancel(ActionEvent event) {
        try {
            Parent adminView = FXMLLoader.load(getClass().getResource("/views/user_login.fxml"));
            SessionManager.getInstance().logout();
            Stage stage = (Stage) cancelBtn.getScene().getWindow();
            stage.setScene(new Scene(adminView));
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
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
            showAlert(Alert.AlertType.ERROR, "错误", "加载界面失败: " + e.getMessage());
        }
    }
    @FXML
    private void updateUser(ActionEvent event) {
        loadFXML("/views/userupdate.fxml", "修改信息", event);
    }


    @FXML
    private void queryBanlance(ActionEvent event) {
        User currentUser = SessionManager.getInstance().getCurrentUser();
        if (currentUser == null) {
            showAlert(Alert.AlertType.ERROR, "错误", "用户未登录");
            return;
        }
        try {
            Integer balance = userService.getUserBalance(currentUser.getId());
            if (balance != null) {
                showAlert(Alert.AlertType.INFORMATION, "余额信息", "当前余额: " + balance);
            } else {
                showAlert(Alert.AlertType.ERROR, "错误", "查询余额失败: 余额为空");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "错误", "查询余额失败: " + e.getMessage());
        }
    }
    private void showAlert(Alert.AlertType alertType, String title, String content) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setContentText(content);
        alert.showAndWait();
    }
    @FXML
    private void delete(ActionEvent event) {
        User currentUser = SessionManager.getInstance().getCurrentUser();
        if (currentUser == null) {
            showAlert(Alert.AlertType.ERROR, "错误", "用户未登录");
            return;
        }

        try {
            // 弹出确认框
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("确认");
            alert.setHeaderText("确定要删除用户吗?");
            alert.setContentText("删除后将无法恢复");
            alert.showAndWait().ifPresent(response -> {
                if (response == ButtonType.OK) {
                    try {
                        userService.deleteUser(currentUser.getId());
                        SessionManager.getInstance().logout();
                        loadFXML("/views/user_login.fxml", "用户登录", event);
                    } catch (SQLException e) {
                        e.printStackTrace();
                        showAlert(Alert.AlertType.ERROR, "错误", "删除用户失败: " + e.getMessage());
                    }
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "错误", "操作失败: " + e.getMessage());
        }
    }
}
