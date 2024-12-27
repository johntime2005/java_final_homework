import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.SQLException;

public class UserPanel extends Application {

    private userService userService;

    @Override
    public void start(Stage primaryStage) {
        try {
            // 获取数据库连接
            Connection connection = DatabaseConnection.getConnection();

            // 创建 BookService 和 UserService 实例
            BookService bookService = new BookService(new bookMegement(connection));
            userService = new userService(new userMegement(connection), bookService);

            // UI 组件和事件处理逻辑
            VBox layout = new VBox(10);
            layout.setPadding(new javafx.geometry.Insets(10));

            // 用户输入字段
            TextField userIdField = new TextField();
            userIdField.setPromptText("Enter User ID");

            TextField bookIdField = new TextField();
            bookIdField.setPromptText("Enter Book ID");

            Button borrowButton = new Button("Borrow Book");

            // 开户功能输入字段
            TextField createNameField = new TextField();
            createNameField.setPromptText("Enter Name");
            TextField createAgeField = new TextField();
            createAgeField.setPromptText("Enter Age");
            TextField createBalanceField = new TextField();
            createBalanceField.setPromptText("Enter Balance");

            Button createAccountButton = new Button("Create Account");

            // 销户功能输入字段
            TextField deleteUserIdField = new TextField();
            deleteUserIdField.setPromptText("Enter User ID to Delete");
            Button deleteAccountButton = new Button("Delete Account");

            // 修改信息功能输入字段
            TextField updateNameField = new TextField();
            updateNameField.setPromptText("Enter New Name");
            TextField updateAgeField = new TextField();
            updateAgeField.setPromptText("Enter New Age");
            TextField updateBalanceField = new TextField();
            updateBalanceField.setPromptText("Enter New Balance");
            Button updateAccountButton = new Button("Update Account");

            // 借书功能
            borrowButton.setOnAction(e -> {
                try {
                    int userId = Integer.parseInt(userIdField.getText());
                    int bookId = Integer.parseInt(bookIdField.getText());

                    // 调用 UserService 的借书方法
                    userService.borrowBook(userId, bookId);

                    // 显示借书成功
                    new Alert(Alert.AlertType.INFORMATION, "Book borrowed successfully!").showAndWait();
                } catch (SQLException ex) {
                    // 显示错误信息
                    System.out.println(ex.getMessage());
                    ex.printStackTrace();
                    new Alert(Alert.AlertType.ERROR, "Error borrowing book: " + ex.getMessage()).showAndWait();
                } catch (NumberFormatException ex) {
                    // 处理用户输入的非数字情况
                    new Alert(Alert.AlertType.ERROR, "Invalid input! Please enter valid numbers.").showAndWait();
                }
            });
            TextField returnUserIdField = new TextField();
            returnUserIdField.setPromptText("Enter User ID to Return Book");

            TextField returnBookIdField = new TextField();
            returnBookIdField.setPromptText("Enter Book ID to Return");

            Button returnButton = new Button("Return Book");
            returnButton.setOnAction(e -> {
                try {
                    int userId = Integer.parseInt(returnUserIdField.getText());
                    int bookId = Integer.parseInt(returnBookIdField.getText());

                    // 调用 UserService 的还书方法
                    userService.returnBook(userId, bookId);

                    // 显示还书成功
                    new Alert(Alert.AlertType.INFORMATION, "Book returned successfully!").showAndWait();
                } catch (SQLException ex) {
                    // 显示错误信息
                    System.out.println(ex.getMessage());
                    ex.printStackTrace();
                    new Alert(Alert.AlertType.ERROR, "Error returning book: " + ex.getMessage()).showAndWait();
                } catch (NumberFormatException ex) {
                    // 处理用户输入的非数字情况
                    new Alert(Alert.AlertType.ERROR, "Invalid input! Please enter valid numbers.").showAndWait();
                }
            });

            // 开户功能
            createAccountButton.setOnAction(e -> {
                try {
                    String name = createNameField.getText();
                    int age = Integer.parseInt(createAgeField.getText());
                    int balance = Integer.parseInt(createBalanceField.getText());

                    // 创建用户对象
                    User newUser = new User(name, age, balance);

                    // 调用 UserService 的创建方法
                    userService.create(newUser);

                    new Alert(Alert.AlertType.INFORMATION, "Account created successfully!").showAndWait();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                    new Alert(Alert.AlertType.ERROR, "Error creating account: " + ex.getMessage()).showAndWait();
                } catch (NumberFormatException ex) {
                    new Alert(Alert.AlertType.ERROR, "Invalid input! Please enter valid numbers.").showAndWait();
                }
            });

            // 销户功能
            deleteAccountButton.setOnAction(e -> {
                try {
                    int userId = Integer.parseInt(deleteUserIdField.getText());

                    userService.delete(userId);

                    new Alert(Alert.AlertType.INFORMATION, "Account deleted successfully!").showAndWait();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                    new Alert(Alert.AlertType.ERROR, "Error deleting account: " + ex.getMessage()).showAndWait();
                } catch (NumberFormatException ex) {
                    new Alert(Alert.AlertType.ERROR, "Invalid input! Please enter valid numbers.").showAndWait();
                }
            });

            // 修改信息功能
            updateAccountButton.setOnAction(e -> {
                try {
                    int userId = Integer.parseInt(userIdField.getText());
                    String newName = updateNameField.getText();
                    int newAge = Integer.parseInt(updateAgeField.getText());
                    int newBalance = Integer.parseInt(updateBalanceField.getText());

                    // 创建更新后的用户对象
                    User updatedUser = new User(userId, newName, newAge, newBalance);

                    // 调用 UserService 的更新方法
                    userService.update(updatedUser);

                    new Alert(Alert.AlertType.INFORMATION, "Account updated successfully!").showAndWait();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                    new Alert(Alert.AlertType.ERROR, "Error updating account: " + ex.getMessage()).showAndWait();
                } catch (NumberFormatException ex) {
                    new Alert(Alert.AlertType.ERROR, "Invalid input! Please enter valid numbers.").showAndWait();
                }
            });

            // 将控件添加到布局
            layout.getChildren().addAll(
                    userIdField, bookIdField, borrowButton,returnButton,
                    createNameField, createAgeField, createBalanceField, createAccountButton,
                    deleteUserIdField, deleteAccountButton,
                    updateNameField, updateAgeField, updateBalanceField, updateAccountButton
            );

            // 设置场景并显示
            Scene scene = new Scene(layout, 400, 600);
            primaryStage.setScene(scene);
            primaryStage.setTitle("User Management");
            primaryStage.show();

        } catch (SQLException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Failed to connect to database!").showAndWait();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
