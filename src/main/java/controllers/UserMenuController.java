package controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.User;

public class UserMenuController {
    @FXML
    private Label functionLabel;
    @FXML
    private Button borrowBookBtn;
    @FXML
    private Button returnBookBtn; // 还书
    @FXML
    private Button modifyBtn;
    @FXML
    private Button cancelBtn; // 返回
    @FXML
    private Button browseBooksBtn; // 浏览书籍

    private Stage stage;
    private User loggedInUser;

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public void setLoggedInUser(User loggedInUser) {
        this.loggedInUser = loggedInUser;
    }

    @FXML
    private void initialize() {
        borrowBookBtn.setOnAction(e -> Borrow(stage, loggedInUser.getId()));
        returnBookBtn.setOnAction(e -> Return(stage, loggedInUser.getId()));
        cancelBtn.setOnAction(e -> showRoleSelectionInterface(stage));
        modifyBtn.setOnAction(e -> Modify(stage, loggedInUser.getId()));
        browseBooksBtn.setOnAction(e -> showBrowseBooksInterface(stage));
    }
    private void showBrowseBooksInterface(Stage stage) {
        // 实现浏览书籍界面的逻辑
        try {
            javafx.fxml.FXMLLoader loader = new javafx.fxml.FXMLLoader(getClass().getResource("/views/browse_books.fxml"));
            javafx.scene.Parent root = loader.load();
            stage.setScene(new javafx.scene.Scene(root));
        } catch (Exception e) {
            showError("无法加载浏览书籍界面", e.getMessage());
        }
    }

    private void Borrow(Stage stage, int userId) {
        // 实现借书界面的逻辑
        try {
            javafx.fxml.FXMLLoader loader = new javafx.fxml.FXMLLoader(getClass().getResource("/views/borrow_book.fxml"));
            javafx.scene.Parent root = loader.load();
            stage.setScene(new javafx.scene.Scene(root));
        } catch (Exception e) {
            showError("无法加载借书界面", e.getMessage());
        }
    }

    private void Return(Stage stage, int userId) {
        // 实现还书界面的逻辑
        try {
            javafx.fxml.FXMLLoader loader = new javafx.fxml.FXMLLoader(getClass().getResource("/views/return_book.fxml"));
            javafx.scene.Parent root = loader.load();
            stage.setScene(new javafx.scene.Scene(root));
        } catch (Exception e) {
            showError("无法加载还书界面", e.getMessage());
        }
    }

    private void Modify(Stage stage, int userId) {
        // 实现修改个人信息界面的逻辑
        try {
            javafx.fxml.FXMLLoader loader = new javafx.fxml.FXMLLoader(getClass().getResource("/views/modify_profile.fxml"));
            javafx.scene.Parent root = loader.load();
            stage.setScene(new javafx.scene.Scene(root));
        } catch (Exception e) {
            showError("无法加载修改个人信息界面", e.getMessage());
        }
    }


    private void showRoleSelectionInterface(Stage stage) {
        // 实现角色选择界面的逻辑
        try {
            javafx.fxml.FXMLLoader loader = new javafx.fxml.FXMLLoader(getClass().getResource("/views/role_selection.fxml"));
            javafx.scene.Parent root = loader.load();
            stage.setScene(new javafx.scene.Scene(root));
        } catch (Exception e) {
            showError("无法加载角色选择界面", e.getMessage());
        }
    }

    private void showError(String title, String content) {
        javafx.scene.control.Alert alert = new javafx.scene.control.Alert(javafx.scene.control.Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setContentText(content);
        alert.showAndWait();
    }
}
