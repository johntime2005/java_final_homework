import javafx.application.Application;
import javafx.application.Platform;
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
            Platform.runLater(() -> {
                Alert alert = new Alert(Alert.AlertType.ERROR, 
                    "数据库连接或初始化失败: " + e.getMessage(), 
                    ButtonType.OK
                );
                alert.showAndWait();
                Platform.exit();
            });
        }
    }

    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage; // 保存主舞台引用
        // 启动时先显示角色选择界面
        showRoleSelectionInterface(primaryStage);
    }

    @Override
    public void stop() {
        DatabaseConnection.closeConnection();
    }

    // ========== 角色选择界面 ==========
    private void showRoleSelectionInterface(Stage stage) {
        VBox roleLayout = new VBox(15);
        roleLayout.setPadding(new javafx.geometry.Insets(10));
        roleLayout.setAlignment(javafx.geometry.Pos.CENTER);

        Label titleLabel = new Label("请选择登录类型");
        Button adminLoginBtn = new Button("管理员登录");
        Button userLoginBtn = new Button("用户登录");

        adminLoginBtn.setOnAction(e -> showAdminLoginInterface(stage));
        userLoginBtn.setOnAction(e -> showUserLoginInterface(stage));

        roleLayout.getChildren().addAll(titleLabel, adminLoginBtn, userLoginBtn);

        scene = new Scene(roleLayout, 400, 300);
        stage.setScene(scene);
        stage.setTitle("选择登录类型");
        stage.show();
    }

    // ========== 管理员登录界面(无注册功能) ==========
    private void showAdminLoginInterface(Stage stage) {
        VBox loginLayout = new VBox(10);
        loginLayout.setPadding(new javafx.geometry.Insets(10));
        loginLayout.setAlignment(javafx.geometry.Pos.CENTER);

        TextField usernameField = new TextField();
        usernameField.setPromptText("管理员用户名");
        PasswordField passwordField = new PasswordField();
        passwordField.setPromptText("密码");

        Button loginButton = new Button("登录");
        Button backButton = new Button("返回");

        loginButton.setOnAction(e -> {
            try {
                User user = userService.login(usernameField.getText(), passwordField.getText());
                if (user != null && "admin".equals(user.getUserType())) {
                    showMainInterface(stage);
                } else {
                    new Alert(Alert.AlertType.ERROR, "管理员用户名或密码错误！").showAndWait();
                }
            } catch (SQLException ex) {
                new Alert(Alert.AlertType.ERROR, "登录失败：" + ex.getMessage()).showAndWait();
            }
        });

        backButton.setOnAction(e -> showRoleSelectionInterface(stage));

        loginLayout.getChildren().addAll(
            new Label("管理员登录"),
            usernameField,
            passwordField,
            loginButton,
            backButton
        );

        scene.setRoot(loginLayout);
        stage.setTitle("管理员登录");
    }

    // ========== 用户登录界面(提供注册功能) ==========
    private void showUserLoginInterface(Stage stage) {
        VBox loginLayout = new VBox(10);
        loginLayout.setPadding(new javafx.geometry.Insets(10));
        loginLayout.setAlignment(javafx.geometry.Pos.CENTER);

        TextField usernameField = new TextField();
        usernameField.setPromptText("用户名");
        PasswordField passwordField = new PasswordField();
        passwordField.setPromptText("密码");

        Button loginButton = new Button("登录");
        Button registerButton = new Button("注册");
        Button backButton = new Button("返回");

        loginButton.setOnAction(e -> {
            try {
                User user = userService.login(usernameField.getText(), passwordField.getText());
                if (user != null && "user".equals(user.getUserType())) {
                    new Alert(Alert.AlertType.INFORMATION, "登录成功，进入用户界面...").showAndWait();
                    showUserMainInterface(stage);
                } else {
                    new Alert(Alert.AlertType.ERROR, "用户名或密码错误，或当前非用户账号！").showAndWait();
                }
            } catch (SQLException ex) {
                new Alert(Alert.AlertType.ERROR, "登录失败：" + ex.getMessage()).showAndWait();
            }
        });

        registerButton.setOnAction(e -> showUserRegisterInterface(stage));
        backButton.setOnAction(e -> showRoleSelectionInterface(stage));

        loginLayout.getChildren().addAll(
            new Label("用户登录"),
            usernameField,
            passwordField,
            loginButton,
            registerButton,
            backButton
        );

        scene.setRoot(loginLayout);
        stage.setTitle("用户登录");
    }

    // ========== 用户注册界面 ==========
    private void showUserRegisterInterface(Stage stage) {
        VBox registerLayout = new VBox(10);
        registerLayout.setPadding(new javafx.geometry.Insets(10));

        TextField usernameField = new TextField();
        usernameField.setPromptText("用户名");
        PasswordField passwordField = new PasswordField();
        passwordField.setPromptText("密码");

        Button registerButton = new Button("注册");
        Button backButton = new Button("返回");

        registerButton.setOnAction(e -> {
            try {
                if (userService.isUsernameExists(usernameField.getText())) {
                    new Alert(Alert.AlertType.ERROR, "用户名已存在！").showAndWait();
                    return;
                }
                User newUser = new User(
                    usernameField.getText(),
                    passwordField.getText(),
                    "user", // 普通用户类型
                    0       // 初始余额
                );
                userService.create(newUser);
                new Alert(Alert.AlertType.INFORMATION, "注册成功！").showAndWait();
                showUserLoginInterface(stage);
            } catch (SQLException ex) {
                new Alert(Alert.AlertType.ERROR, "注册失败：" + ex.getMessage()).showAndWait();
            }
        });

        backButton.setOnAction(e -> showUserLoginInterface(stage));

        registerLayout.getChildren().addAll(
            new Label("用户注册"),
            usernameField,
            passwordField,
            registerButton,
            backButton
        );

        scene.setRoot(registerLayout);
        stage.setTitle("用户注册");
    }

    private void showMainInterface(Stage stage) {
        // 主界面布局
        mainLayout = new VBox(10);
        mainLayout.setPadding(new javafx.geometry.Insets(10));
        mainLayout.setAlignment(javafx.geometry.Pos.CENTER);

        // 创建功能按钮
        Button queryBookBtn = new Button("查询书籍");
        Button addBookBtn = new Button("添加书籍");
        Button advancedSearchBtn = new Button("高级查询");
        
        // 设置按钮样式和大小
        queryBookBtn.setPrefWidth(200);
        addBookBtn.setPrefWidth(200);
        advancedSearchBtn.setPrefWidth(200);

        // 添加按钮点击事件
        queryBookBtn.setOnAction(e -> showQueryInterface());
        addBookBtn.setOnAction(e -> showAddInterface());
        advancedSearchBtn.setOnAction(e -> showAdvancedQueryInterface());

        // 将按钮添加到主布局
        mainLayout.getChildren().addAll(
            new Label("图书管理系统 - 管理员界面"),
            queryBookBtn,
            addBookBtn,
            advancedSearchBtn
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
                LocalDate publishDate = publishDatePicker.getValue();
                if (publishDate == null) {
                    new Alert(Alert.AlertType.WARNING, "请先选择出版日期").showAndWait();
                    return;
                }
                String title = titleField.getText();
                String author = authorField.getText();
                String publisher = publisherField.getText();
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

    private void showAdvancedQueryInterface() {
        VBox advLayout = new VBox(10);
        advLayout.setPadding(new javafx.geometry.Insets(10));

        TextField titleField = new TextField();
        titleField.setPromptText("书名 (可选)");
        TextField authorField = new TextField();
        authorField.setPromptText("作者 (可选)");
        TextField publisherField = new TextField();
        publisherField.setPromptText("出版社 (可选)");

        Button searchBtn = new Button("搜索");
        Button backBtn = new Button("返回主菜单");

        searchBtn.setOnAction(e -> {
            try {
                List<Book> books = bookService.searchBooks(
                    titleField.getText(),
                    authorField.getText(),
                    publisherField.getText()
                );
                if (books.isEmpty()) {
                    new Alert(Alert.AlertType.INFORMATION, "未找到符合条件的书籍").showAndWait();
                } else {
                    StringBuilder sb = new StringBuilder("查询结果:\n");
                    for (Book book : books) {
                        sb.append(book).append("\n");
                    }
                    new Alert(Alert.AlertType.INFORMATION, sb.toString()).showAndWait();
                }
            } catch (SQLException ex) {
                new Alert(Alert.AlertType.ERROR, "查询失败: " + ex.getMessage()).showAndWait();
            }
        });

        backBtn.setOnAction(e -> scene.setRoot(mainLayout));

        advLayout.getChildren().addAll(
            new Label("高级查询 - 可任意组合条件"),
            titleField,
            authorField,
            publisherField,
            searchBtn,
            backBtn
        );
        scene.setRoot(advLayout);
    }

    // 显示普通用户界面
    private void showUserMainInterface(Stage stage) {
        VBox userLayout = new VBox(10);
        userLayout.setPadding(new javafx.geometry.Insets(10));
        userLayout.setAlignment(javafx.geometry.Pos.CENTER);

        // 在此添加用户可执行的功能按钮
        Button browseBooksBtn = new Button("浏览书籍");
        browseBooksBtn.setPrefWidth(200);
        browseBooksBtn.setOnAction(e -> showBrowseBooksInterface(stage));

        Button logoutBtn = new Button("退出登录");
        logoutBtn.setPrefWidth(200);
        logoutBtn.setOnAction(e -> showRoleSelectionInterface(stage));

        userLayout.getChildren().addAll(
            new Label("欢迎进入用户界面"),
            browseBooksBtn,
            logoutBtn
        );

        scene.setRoot(userLayout);
        stage.setTitle("用户面板");
    }

    private void showBrowseBooksInterface(Stage stage) {
        VBox browseLayout = new VBox(10);
        browseLayout.setPadding(new javafx.geometry.Insets(10));

        ListView<String> listView = new ListView<>();
        try {
            List<Book> allBooks = bookService.getAllBooks();
            for (Book bk : allBooks) {
                listView.getItems().add(bk.toString());
            }
        } catch (SQLException ex) {
            new Alert(Alert.AlertType.ERROR, "获取书籍列表失败: " + ex.getMessage()).showAndWait();
        }

        Button backBtn = new Button("返回用户界面");
        backBtn.setPrefWidth(200);
        backBtn.setOnAction(e -> showUserMainInterface(stage));

        browseLayout.getChildren().addAll(
            new Label("书籍列表"),
            listView,
            backBtn
        );
        scene.setRoot(browseLayout);
        stage.setTitle("用户界面 - 浏览书籍");
    }

    public static void main(String[] args) {
        launch(args);
    }
}