import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

public class AdminPanel extends Application {
    private BookService bookService;
    private userMegementDao userService;
    private VBox mainLayout;
    private Scene scene;
    private Stage primaryStage; // 添加主舞台引用

    // 构造函数
    public AdminPanel() {
        try {
            // 获取数据库连接
            Connection connection = DatabaseConnection.getConnection();

            // 创建 BookService 实例，传入 BookDao 实现（即 bookMegement）
            this.bookService = new BookService(new bookMegement(connection)); // 传入数据库连接给 BookService
            this.userService = new userMegement(connection);

        } catch (SQLException e) {
            e.printStackTrace();
            // 如果连接数据库失败，弹出错误提示
            new Alert(Alert.AlertType.ERROR, "连接数据库失败!").showAndWait();
        }
    }

    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage; // 保存主舞台引用
        showLoginInterface(primaryStage);
    }

    private void showLoginInterface(Stage stage) {
        VBox loginLayout = new VBox(10);
        loginLayout.setPadding(new javafx.geometry.Insets(10));
        loginLayout.setAlignment(javafx.geometry.Pos.CENTER);

        TextField usernameField = new TextField();
        usernameField.setPromptText("用户名");
        PasswordField passwordField = new PasswordField();
        passwordField.setPromptText("密码");

        Button loginButton = new Button("登录");
        Button registerButton = new Button("注册");

        loginButton.setOnAction(e -> {
            try {
                User user = userService.login(usernameField.getText(), passwordField.getText());
                if (user != null && "admin".equals(user.getUserType())) {
                    showMainInterface(stage);
                } else {
                    new Alert(Alert.AlertType.ERROR, "用户名或密码错误，或无管理员权限！").showAndWait();
                }
            } catch (SQLException ex) {
                new Alert(Alert.AlertType.ERROR, "登录失败：" + ex.getMessage()).showAndWait();
            }
        });

        registerButton.setOnAction(e -> showRegisterInterface(stage));

        loginLayout.getChildren().addAll(
            new Label("管理员登录"),
            usernameField,
            passwordField,
            loginButton,
            registerButton
        );

        scene = new Scene(loginLayout, 400, 500);
        stage.setScene(scene);
        stage.setTitle("登录");
        stage.show();
    }

    private void showRegisterInterface(Stage stage) {
        VBox registerLayout = new VBox(10);
        registerLayout.setPadding(new javafx.geometry.Insets(10));

        TextField nameField = new TextField();
        nameField.setPromptText("姓名");
        TextField usernameField = new TextField();
        usernameField.setPromptText("用户名");
        PasswordField passwordField = new PasswordField();
        passwordField.setPromptText("密码");
        TextField ageField = new TextField();
        ageField.setPromptText("年龄");

        Button registerButton = new Button("注册");
        Button backButton = new Button("返回登录");

        registerButton.setOnAction(e -> {
            try {
                if (userService.isUsernameExists(usernameField.getText())) {
                    new Alert(Alert.AlertType.ERROR, "用户名已存在！").showAndWait();
                    return;
                }

                User newUser = new User(
                    nameField.getText(),
                    Integer.parseInt(ageField.getText()),
                    0,
                    usernameField.getText(),
                    passwordField.getText(),
                    "user"
                );
                userService.create(newUser);
                new Alert(Alert.AlertType.INFORMATION, "注册成功！").showAndWait();
                showLoginInterface(stage); // 注册成功后返回登录界面
            } catch (SQLException ex) {
                new Alert(Alert.AlertType.ERROR, "注册失败：" + ex.getMessage()).showAndWait();
            }
        });

        backButton.setOnAction(e -> showLoginInterface(stage)); // 直接调用显示登录界面

        registerLayout.getChildren().addAll(
            new Label("用户注册"),
            nameField,
            usernameField,
            passwordField,
            ageField,
            registerButton,
            backButton
        );

        // 创建新的场景或更新现有场景
        if (scene == null) {
            scene = new Scene(registerLayout, 400, 500);
            stage.setScene(scene);
        } else {
            scene.setRoot(registerLayout);
        }
        stage.setTitle("注册");
    }

    private void showMainInterface(Stage stage) {
        // 主界面布局
        mainLayout = new VBox(10);
        mainLayout.setPadding(new javafx.geometry.Insets(10));
        mainLayout.setAlignment(javafx.geometry.Pos.CENTER);

        // 创建功能按钮
        Button queryBookBtn = new Button("查询书籍");
        Button addBookBtn = new Button("添加书籍");
        
        // 设置按钮样式和大小
        queryBookBtn.setPrefWidth(200);
        addBookBtn.setPrefWidth(200);

        // 添加按钮点击事件
        queryBookBtn.setOnAction(e -> showQueryInterface());
        addBookBtn.setOnAction(e -> showAddInterface());

        // 将按钮添加到主布局
        mainLayout.getChildren().addAll(
            new Label("图书管理系统 - 管理员界面"),
            queryBookBtn,
            addBookBtn
        );

        scene = new Scene(mainLayout, 400, 500);
        stage.setScene(scene);
        stage.setTitle("管理员面板");
        stage.show();
    }

    // 显示查询界面
    private void showQueryInterface() {
        VBox queryLayout = new VBox(10);
        queryLayout.setPadding(new javafx.geometry.Insets(10));

        TextField bookTitleField = new TextField();
        bookTitleField.setPromptText("输入书名以查询");
        Button queryButton = new Button("搜索");
        Button backButton = new Button("返回主菜单");

        queryButton.setOnAction(e -> {
            String title = bookTitleField.getText();
            try {
                List<Book> books = bookService.getBookByTitle(title);
                if (books.isEmpty()) {
                    new Alert(Alert.AlertType.INFORMATION, "不存在该书名书籍").showAndWait();
                } else {
                    StringBuilder result = new StringBuilder("找到书籍:\n");
                    books.forEach(book -> result.append(book).append("\n"));
                    new Alert(Alert.AlertType.INFORMATION, result.toString()).showAndWait();
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
                new Alert(Alert.AlertType.ERROR, "查询书籍失败").showAndWait();
            }
        });

        backButton.setOnAction(e -> scene.setRoot(mainLayout));

        queryLayout.getChildren().addAll(
            new Label("查询书籍"),
            bookTitleField,
            queryButton,
            backButton
        );

        scene.setRoot(queryLayout);
    }

    // 显示添加界面
    private void showAddInterface() {
        VBox addLayout = new VBox(10);
        addLayout.setPadding(new javafx.geometry.Insets(10));

        TextField titleField = new TextField();
        titleField.setPromptText("书名");
        TextField authorField = new TextField();
        authorField.setPromptText("作者");
        TextField publisherField = new TextField();
        publisherField.setPromptText("出版社");
        DatePicker publishDatePicker = new DatePicker();
        publishDatePicker.setPromptText("出版日期");
        TextField isbnField = new TextField();
        isbnField.setPromptText("ISBN");
        TextField quantityField = new TextField();
        quantityField.setPromptText("数量");

        Button addButton = new Button("添加");
        Button backButton = new Button("返回主菜单");

        addButton.setOnAction(e -> {
            try {
                String title = titleField.getText();
                String author = authorField.getText();
                String publisher = publisherField.getText();
                LocalDate publishDate = publishDatePicker.getValue();
                String isbn = isbnField.getText();
                int quantity = Integer.parseInt(quantityField.getText());

                Book book = new Book(0, title, author, publisher, publishDate, isbn, quantity);
                bookService.addBook(book);

                titleField.clear();
                authorField.clear();
                publisherField.clear();
                publishDatePicker.setValue(null);
                isbnField.clear();
                quantityField.clear();

                new Alert(Alert.AlertType.INFORMATION, "书籍添加成功！").showAndWait();
            } catch (SQLException ex) {
                ex.printStackTrace();
                new Alert(Alert.AlertType.ERROR, "书籍添加失败!").showAndWait();
            } catch (NumberFormatException ex) {
                new Alert(Alert.AlertType.WARNING, "请输入有效的数量.").showAndWait();
            }
        });

        backButton.setOnAction(e -> scene.setRoot(mainLayout));

        addLayout.getChildren().addAll(
            new Label("添加新书"),
            titleField,
            authorField,
            publisherField,
            publishDatePicker,
            isbnField,
            quantityField,
            addButton,
            backButton
        );

        scene.setRoot(addLayout);
    }

    public static void main(String[] args) {
        launch(args);
    }
}