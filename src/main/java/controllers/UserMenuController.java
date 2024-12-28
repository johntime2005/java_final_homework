package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
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
        borrowBookBtn.setOnAction(this::Borrow);
        returnBookBtn.setOnAction(this::Return);
        cancelBtn.setOnAction(this::showRoleSelectionInterface);
        modifyBtn.setOnAction(this::Modify);
        browseBooksBtn.setOnAction(this::showBrowseBooksInterface);
    }

    public void showBrowseBooksInterface(ActionEvent event) {
        // 实现浏览书籍界面的逻辑
        try {
            javafx.fxml.FXMLLoader loader = new javafx.fxml.FXMLLoader(getClass().getResource("/views/browse_books.fxml"));
            Parent root = loader.load();
            stage.setScene(new Scene(root));
        } catch (Exception e) {
            showError("无法加载浏览书籍界面", e.getMessage());
        }
    }

    public void Borrow(ActionEvent event) {
        // 实现借书界面的逻辑
        try {
            javafx.fxml.FXMLLoader loader = new javafx.fxml.FXMLLoader(getClass().getResource("/views/borrow_book.fxml"));
            Parent root = loader.load();
            stage.setScene(new Scene(root));
        } catch (Exception e) {
            showError("无法加载借书界面", e.getMessage());
        }
    }

    public void Return(ActionEvent event) {
        // 实现还书界面的逻辑
        try {
            javafx.fxml.FXMLLoader loader = new javafx.fxml.FXMLLoader(getClass().getResource("/views/return_book.fxml"));
            Parent root = loader.load();
            stage.setScene(new Scene(root));
        } catch (Exception e) {
            showError("无法加载还书界面", e.getMessage());
        }
    }

    public void Modify(ActionEvent event) {
        // 实现修改个人信息界面的逻辑
        try {
            javafx.fxml.FXMLLoader loader = new javafx.fxml.FXMLLoader(getClass().getResource("/views/modify_profile.fxml"));
            Parent root = loader.load();
            stage.setScene(new Scene(root));
        } catch (Exception e) {
            showError("无法加载修改个人信息界面", e.getMessage());
        }
    }

    public void showRoleSelectionInterface(ActionEvent event) {
        // 实现角色选择界面的逻辑
        try {
            javafx.fxml.FXMLLoader loader = new javafx.fxml.FXMLLoader(getClass().getResource("/views/role_selection.fxml"));
            Parent root = loader.load();
            stage.setScene(new Scene(root));
        } catch (Exception e) {
            showError("无法加载角色选择界面", e.getMessage());
        }
    }

    public void showError(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setContentText(content);
        alert.showAndWait();
    }
}
