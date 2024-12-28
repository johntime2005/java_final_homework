package utils;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import dao.userMegementDao;
import impl.bookMegement;
import impl.userMegement;
import model.Book;
import model.User;
import dao.userMegementDao;
import impl.bookMegement;
import impl.userMegement;
import model.Book;
import model.User;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.*;

import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

public class AdminPanel extends Application {
    private BookService bookService;
    private userMegementDao userService;
    private VBox mainLayout;
    private Scene scene;
    private User loggedInUser;

    public AdminPanel() {
        try {
            Connection connection = DatabaseConnection.getConnection();
            this.bookService = new BookService(new bookMegement(connection));
            this.userService = new userMegement(connection);
        } catch (SQLException e) {
            e.printStackTrace();
            Platform.runLater(() -> {
                Alert alert = new Alert(Alert.AlertType.ERROR,
                        "数据库连接或初始化失败: " + e.getMessage(),
                        ButtonType.OK);
                alert.showAndWait();
                Platform.exit();
            });
        }
    }

    @Override
    public void start(Stage primaryStage) {
        try {
            // 修改为加载角色选择界面
            Parent root = FXMLLoader.load(getClass().getResource("/views/login.fxml"));
            Scene scene = new Scene(root);
            primaryStage.setScene(scene);
            primaryStage.setTitle("图书管理系统");
            primaryStage.show();
        } catch (Exception e) {
            e.printStackTrace();
            showError("启动失败", e.getMessage());
        }
    }

    private void showError(String title, String content) {
        Platform.runLater(() -> {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle(title);
            alert.setContentText(content);
            alert.showAndWait();
        });
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
                    loggedInUser = user;// 将用户对象存储在类级别的变量中
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
                backButton);

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
                    loggedInUser = user;// 将用户对象存储在类级别的变量中
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
                backButton);

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
                        10 // 初始余额
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
                backButton);

        scene.setRoot(registerLayout);
        stage.setTitle("用户注册");
    }

    private void showMainInterface(Stage stage) {
        // 主界面布局
        mainLayout = new VBox(10);
        mainLayout.setPadding(new javafx.geometry.Insets(10));
        mainLayout.setAlignment(javafx.geometry.Pos.CENTER);

        // 创建功能按钮
        Button combinedQueryBtn = new Button("综合查询");
        combinedQueryBtn.setPrefWidth(200);
        combinedQueryBtn.setOnAction(e -> showCombinedQueryInterface());

        Button addBookBtn = new Button("添加书籍");
        addBookBtn.setPrefWidth(200);
        addBookBtn.setOnAction(e -> showAddInterface());

        Button logoutBtn = new Button("退出登录");
        logoutBtn.setPrefWidth(200);
        logoutBtn.setOnAction(e -> showRoleSelectionInterface(stage));

        // 将按钮添加到主布局
        mainLayout.getChildren().addAll(
                new Label("图书管理系统 - 管理员界面"),
                combinedQueryBtn,
                addBookBtn,
                logoutBtn);

        scene = new Scene(mainLayout, 400, 500);
        stage.setScene(scene);
        stage.setTitle("管理员面板");
        stage.show();
    }

    private void showCombinedQueryInterface() {
        VBox combinedLayout = new VBox(10);
        combinedLayout.setPadding(new javafx.geometry.Insets(10));

        TextField titleField = new TextField();
        titleField.setPromptText("书名 (可选)");
        TextField authorField = new TextField();
        authorField.setPromptText("作者 (可选)");
        TextField publisherField = new TextField();
        publisherField.setPromptText("出版社 (可选)");

        Button searchBtn = new Button("搜索");
        searchBtn.setOnAction(e -> {
            try {
                List<Book> books = bookService.searchBooks(
                        titleField.getText(),
                        authorField.getText(),
                        publisherField.getText());
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

        Button backBtn = new Button("返回主菜单");
        backBtn.setOnAction(e -> scene.setRoot(mainLayout));

        combinedLayout.getChildren().addAll(
                new Label("综合查询 - 可根据任意条件搜索"),
                titleField, authorField, publisherField,
                searchBtn, backBtn);
        scene.setRoot(combinedLayout);
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

        Button addButton = new Button("添加");
        Button backButton = new Button("返回主菜单");

        addButton.setOnAction(e -> {
            try {
                String title = titleField.getText();
                String author = authorField.getText();
                String publisher = publisherField.getText();

                Book book = new Book(0, title, author, publisher, false); // 只传递书名、作者和出版社
                bookService.addBook(book);

                titleField.clear();
                authorField.clear();
                publisherField.clear();

                new Alert(Alert.AlertType.INFORMATION, "书籍添加成功！").showAndWait();
            } catch (SQLException ex) {
                ex.printStackTrace();
                new Alert(Alert.AlertType.ERROR, "书籍添加失败!").showAndWait();
            }
        });

        backButton.setOnAction(e -> scene.setRoot(mainLayout));

        addLayout.getChildren().addAll(
                new Label("添加新书"),
                titleField,
                authorField,
                publisherField,
                addButton,
                backButton);

        scene.setRoot(addLayout);
    }
    private void showReturnBookInterface(Stage stage, int userId) {
        VBox returnLayout = new VBox(10);
        returnLayout.setPadding(new javafx.geometry.Insets(10));

        TableView<Book> tableView = new TableView<>();
        TableColumn<Book, Integer> idCol = new TableColumn<>("ID");
        idCol.setCellValueFactory(new PropertyValueFactory<>("id"));

        TableColumn<Book, String> titleCol = new TableColumn<>("书名");
        titleCol.setCellValueFactory(new PropertyValueFactory<>("title"));

        TableColumn<Book, String> authorCol = new TableColumn<>("作者");
        authorCol.setCellValueFactory(new PropertyValueFactory<>("author"));

        TableColumn<Book, String> publisherCol = new TableColumn<>("出版社");
        publisherCol.setCellValueFactory(new PropertyValueFactory<>("publisher"));

        TableColumn<Book, Boolean> isborrowedCol = new TableColumn<>("Is Borrowed");
        isborrowedCol.setCellValueFactory(new PropertyValueFactory<>("isborrowed"));

        tableView.getColumns().addAll(idCol, titleCol, authorCol, publisherCol, isborrowedCol);

        // 设置可选择的模式，使得只能单选
        tableView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);

        try {
            List<Book> allBooks = bookService.getAllBooks(); // 假设这个方法返回所有已借出的书籍
            tableView.setItems(javafx.collections.FXCollections.observableArrayList(allBooks));
        } catch (SQLException ex) {
            new Alert(Alert.AlertType.ERROR, "获取书籍列表失败: " + ex.getMessage()).showAndWait();
            return;
        }

        Button returnBtn = new Button("还书");
        returnBtn.setPrefWidth(200);
        returnBtn.setOnAction(e -> {
            Book selectedBook = tableView.getSelectionModel().getSelectedItem();
            if (selectedBook != null) {
                try {
                    userService.returnBook(userId, selectedBook.getId()); // 调用还书方法
                    new Alert(Alert.AlertType.INFORMATION, "还书成功！").showAndWait();
                    showBrowseBooksInterface(stage); // 还书后返回书籍浏览界面
                } catch (SQLException ex) {
                    new Alert(Alert.AlertType.ERROR, "还书失败: " + ex.getMessage()).showAndWait();
                }
            } else {
                new Alert(Alert.AlertType.WARNING, "请先选择一本书！").showAndWait();
            }
        });

        Button backBtn = new Button("返回");
        backBtn.setPrefWidth(200);
        backBtn.setOnAction(e -> showBrowseBooksInterface(stage));

        returnLayout.getChildren().addAll(
                new Label("选择书籍归还"),
                tableView,
                returnBtn,
                backBtn
        );

        Scene scene = new Scene(returnLayout, 600, 400); // 创建或重新使用Scene
        stage.setScene(scene);
        stage.setTitle("用户界面 - 还书");
        stage.show();
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
        Button borrowBookBtn = new Button("借书"); // 新增借书按钮
        borrowBookBtn.setPrefWidth(200);
        borrowBookBtn.setOnAction(e -> showBorrowBookInterface(stage, loggedInUser.getId()));
        Button returnBookBtn = new Button("还书"); // 新增还书按钮
        returnBookBtn.setPrefWidth(200);
        returnBookBtn.setOnAction(e -> showReturnBookInterface(stage, loggedInUser.getId()));
        // 假设用户ID为1，实际应用中需要从登录信息中获取
        Button logoutBtn = new Button("退出登录");
        logoutBtn.setPrefWidth(200);
        logoutBtn.setOnAction(e -> showRoleSelectionInterface(stage));

        userLayout.getChildren().addAll(
                new Label("欢迎进入用户界面"),
                browseBooksBtn,
                borrowBookBtn,
                returnBookBtn,
            logoutBtn
        );

        scene.setRoot(userLayout);
        stage.setTitle("用户面板");
    }

    private void showBrowseBooksInterface(Stage stage) {
        VBox browseLayout = new VBox(10);
        browseLayout.setPadding(new javafx.geometry.Insets(10));

        TableView<Book> tableView = new TableView<>();
        TableColumn<Book, Integer> idCol = new TableColumn<>("ID");
        idCol.setCellValueFactory(new PropertyValueFactory<>("id"));

        TableColumn<Book, String> titleCol = new TableColumn<>("书名");
        titleCol.setCellValueFactory(new PropertyValueFactory<>("title"));

        TableColumn<Book, String> authorCol = new TableColumn<>("作者");
        authorCol.setCellValueFactory(new PropertyValueFactory<>("author"));

        TableColumn<Book, String> publisherCol = new TableColumn<>("出版社");
        publisherCol.setCellValueFactory(new PropertyValueFactory<>("publisher"));
        TableColumn<Book, Boolean> isborrowedCol = new TableColumn<>("Is Borrowed");
        isborrowedCol.setCellValueFactory(new PropertyValueFactory<>("isborrowed"));
        isborrowedCol.setCellFactory(column -> new TableCell<Book, Boolean>() {
            @Override
            protected void updateItem(Boolean item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                } else {
                    setText(item ? "true" : "false");
                }
            }
        });

        // TableColumn<Book, String> dateCol = new TableColumn<>("出版日期");
        // dateCol.setCellValueFactory(new PropertyValueFactory<>("publishDate"));

        // TableColumn<Book, String> isbnCol = new TableColumn<>("ISBN");
        // isbnCol.setCellValueFactory(new PropertyValueFactory<>("isbn"));

        // TableColumn<Book, Integer> qtyCol = new TableColumn<>("数量");
        // qtyCol.setCellValueFactory(new PropertyValueFactory<>("quantity"));

        tableView.getColumns().add(idCol);
        tableView.getColumns().add(titleCol);
        tableView.getColumns().add(authorCol);
        tableView.getColumns().add(publisherCol);
        tableView.getColumns().add(isborrowedCol);
        // tableView.getColumns().add(dateCol);
        // tableView.getColumns().add(isbnCol);
        // tableView.getColumns().add(qtyCol);

        try {
            List<Book> allBooks = bookService.getAllBooks();
            tableView.setItems(javafx.collections.FXCollections.observableArrayList(allBooks));
        } catch (SQLException ex) {
            new Alert(Alert.AlertType.ERROR, "获取书籍列表失败: " + ex.getMessage()).showAndWait();
        }

        Button backBtn = new Button("返回用户界面");
        backBtn.setPrefWidth(200);
        backBtn.setOnAction(e -> showUserMainInterface(stage));

        browseLayout.getChildren().addAll(
                new Label("书籍列表"),
                tableView,
                backBtn);
        scene.setRoot(browseLayout);
        stage.setTitle("用户界面 - 浏览书籍");
    }

    private void showBorrowBookInterface(Stage stage, int userId) {
        VBox borrowLayout = new VBox(10);
        borrowLayout.setPadding(new javafx.geometry.Insets(10));

        TableView<Book> tableView = new TableView<>();
        TableColumn<Book, Integer> idCol = new TableColumn<>("ID");
        idCol.setCellValueFactory(new PropertyValueFactory<>("id"));

        TableColumn<Book, String> titleCol = new TableColumn<>("书名");
        titleCol.setCellValueFactory(new PropertyValueFactory<>("title"));

        TableColumn<Book, String> authorCol = new TableColumn<>("作者");
        authorCol.setCellValueFactory(new PropertyValueFactory<>("author"));

        TableColumn<Book, String> publisherCol = new TableColumn<>("出版社");
        publisherCol.setCellValueFactory(new PropertyValueFactory<>("publisher"));
        TableColumn<Book, Boolean> isborrowedCol = new TableColumn<>("Is Borrowed");
        isborrowedCol.setCellValueFactory(new PropertyValueFactory<>("isborrowed"));

        // TableColumn<Book, String> isbnCol = new TableColumn<>("ISBN");
        // isbnCol.setCellValueFactory(new PropertyValueFactory<>("isbn"));

        // TableColumn<Book, Integer> quantityCol = new TableColumn<>("数量");
        // quantityCol.setCellValueFactory(new PropertyValueFactory<>("quantity"));

        tableView.getColumns().addAll(idCol, titleCol, authorCol, isborrowedCol);
        // 设置可选择的模式，使得只能单选
        tableView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);

        try {
            List<Book> allBooks = bookService.getAllBooks(); // 假设这个方法返回所有可借的书籍
            tableView.setItems(javafx.collections.FXCollections.observableArrayList(allBooks));
        } catch (SQLException ex) {
            new Alert(Alert.AlertType.ERROR, "获取书籍列表失败: " + ex.getMessage()).showAndWait();
            return;
        }

        Button borrowBtn = new Button("借书");
        borrowBtn.setPrefWidth(200);
        borrowBtn.setOnAction(e -> {
            Book selectedBook = tableView.getSelectionModel().getSelectedItem();
            if (selectedBook != null) {
                try {
                    userService.borrowBook(userId, selectedBook.getId()); // 调用借书方法
                    new Alert(Alert.AlertType.INFORMATION, "借书成功！").showAndWait();
                    showBrowseBooksInterface(stage); // 借书后返回书籍浏览界面
                } catch (SQLException ex) {
                    new Alert(Alert.AlertType.ERROR, "借书失败: " + ex.getMessage()).showAndWait();
                }
            } else {
                new Alert(Alert.AlertType.WARNING, "请先选择一本书！").showAndWait();
            }
        });

        Button backBtn = new Button("返回");
        backBtn.setPrefWidth(200);
        backBtn.setOnAction(e -> showBrowseBooksInterface(stage));

        borrowLayout.getChildren().addAll(
                new Label("选择书籍借出"),
                tableView,
                borrowBtn,
                backBtn);

        Scene scene = new Scene(borrowLayout, 600, 400); // 创建或重新使用Scene
        stage.setScene(scene);
        stage.setTitle("用户界面 - 借书");
        stage.show();
    }

    // 确保Book类有一个getId()方法
    // public class Book {
    // private Integer id;
    // private String title;
    // private String author;
    // // 其他属性和方法
    // public Integer getId() {
    // return id;
    // }
    // // 其他getter和setter
    // }
    public static void main(String[] args) {
        launch(args);
    }
}