import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import java.sql.SQLException;
import java.util.List;

public class UserPanel extends Application {
    private userService userService;

    // GUI组件
    private TextField userIdField, userNameField, userAgeField, userBalanceField;
    private Button registerButton, queryButton, updateButton;
    private TextArea userInfoArea;

    public UserPanel(userService userService) {
        this.userService = userService;
    }

    @Override
    public void start(Stage primaryStage) {
        // 初始化GUI组件
        userIdField = new TextField();
        userIdField.setPromptText("Enter User ID");

        userNameField = new TextField();
        userNameField.setPromptText("Enter Name");

        userAgeField = new TextField();
        userAgeField.setPromptText("Enter Age");

        userBalanceField = new TextField();
        userBalanceField.setPromptText("Enter Balance");

        registerButton = new Button("Register User");
        queryButton = new Button("Query User");
        updateButton = new Button("Update User");

        userInfoArea = new TextArea();
        userInfoArea.setEditable(false);

        // 用户注册按钮事件
        registerButton.setOnAction(e -> {
            try {
                int id = Integer.parseInt(userIdField.getText());
                String name = userNameField.getText();
                int age = Integer.parseInt(userAgeField.getText());
                int balance = Integer.parseInt(userBalanceField.getText());

                User newUser = new User(id, name, age, balance);
                userService.createUser(newUser);
                userInfoArea.setText("User registered successfully!");
            } catch (SQLException | NumberFormatException ex) {
                ex.printStackTrace();
                userInfoArea.setText("Error: " + ex.getMessage());
            }
        });

        // 查询用户按钮事件
        queryButton.setOnAction(e -> {
            try {
                int id = Integer.parseInt(userIdField.getText());
                List<User> users = userService.getUserById(id);
                if (users.isEmpty()) {
                    userInfoArea.setText("User not found!");
                } else {
                    StringBuilder userDetails = new StringBuilder();
                    for (User user : users) {
                        userDetails.append(user.toString()).append("\n");
                    }
                    userInfoArea.setText(userDetails.toString());
                }
            } catch (SQLException | NumberFormatException ex) {
                ex.printStackTrace();
                userInfoArea.setText("Error: " + ex.getMessage());
            }
        });

        // 修改用户按钮事件
        updateButton.setOnAction(e -> {
            try {
                int id = Integer.parseInt(userIdField.getText());
                String name = userNameField.getText();
                int age = Integer.parseInt(userAgeField.getText());
                int balance = Integer.parseInt(userBalanceField.getText());

                User updatedUser = new User(id, name, age, balance);
                userService.updateUser(updatedUser);
                userInfoArea.setText("User updated successfully!");
            } catch (SQLException | NumberFormatException ex) {
                ex.printStackTrace();
                userInfoArea.setText("Error: " + ex.getMessage());
            }
        });

        // 布局
        VBox layout = new VBox(10, userIdField, userNameField, userAgeField, userBalanceField,
                registerButton, queryButton, updateButton, userInfoArea);
        Scene scene = new Scene(layout, 400, 400);

        primaryStage.setScene(scene);
        primaryStage.setTitle("User Management");
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
