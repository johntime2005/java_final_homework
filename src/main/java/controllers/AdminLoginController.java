package controllers;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;
import model.User;
import dao.userMegementDao;
import impl.userMegement;
import utils.AdminPanel;
import utils.DatabaseConnection;

import java.sql.Connection;
import java.sql.SQLException;

public class AdminLoginController {
    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Button loginButton;

    @FXML
    private Button backButton;

    private userMegementDao userService;
    private AdminPanel adminPanel;
    private Stage stage;

    public AdminLoginController() {
        try {
            Connection connection = DatabaseConnection.getConnection();
            this.userService = new userMegement(connection);
        } catch (SQLException e) {
            e.printStackTrace();
            Platform.runLater(() -> {
                Alert alert = new Alert(AlertType.ERROR,
                        "数据库连接或初始化失败: " + e.getMessage(),
                        ButtonType.OK);
                alert.showAndWait();
                Platform.exit();
            });
        }
    }

    public void setAdminPanel(AdminPanel adminPanel) {
        this.adminPanel = adminPanel;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    @FXML
    private void initialize() {
        loginButton.setOnAction(e -> handleLogin());
        backButton.setOnAction(e -> handleBack());
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
