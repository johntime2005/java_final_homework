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

    // 构造函数
    public AdminPanel() {
        try {
            // 获取数据库连接
            Connection connection = DatabaseConnection.getConnection();

            // 创建 BookService 实例，传入 BookDao 实现（即 bookMegement）
            this.bookService = new BookService(new bookMegement(connection)); // 传入数据库连接给 BookService

        } catch (SQLException e) {
            e.printStackTrace();
            // 如果连接数据库失败，弹出错误提示
            new Alert(Alert.AlertType.ERROR, "连接数据库失败!").showAndWait();
        }
    }

    @Override
    public void start(Stage primaryStage) {
        // 主界面布局
        VBox layout = new VBox(10);
        layout.setPadding(new javafx.geometry.Insets(10));

        // 输入查询书籍信息的控件
        TextField bookTitleField = new TextField();
        bookTitleField.setPromptText("输入书名以查询");

        Button queryButton = new Button("搜索");

        // 添加书籍控件
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

        // 查询书籍功能
        queryButton.setOnAction(e -> {
            String title = bookTitleField.getText();
            try {
                List<Book> books = bookService.getBookByTitle(title);
                if (books.isEmpty()) {
                    new Alert(Alert.AlertType.INFORMATION, "不存在该书名书籍").showAndWait();
                } else {
                    // 显示查询结果
                    StringBuilder result = new StringBuilder("找到书籍:\n");
                    books.forEach(book -> result.append(book).append("\n"));
                    new Alert(Alert.AlertType.INFORMATION, result.toString()).showAndWait();
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
                new Alert(Alert.AlertType.ERROR, "查询书籍失败").showAndWait();
            }
        });

        // 添加书籍功能
        addButton.setOnAction(e -> {
            try {
                String title = titleField.getText();
                String author = authorField.getText();
                String publisher = publisherField.getText();
                LocalDate publishDate = publishDatePicker.getValue();
                String isbn = isbnField.getText();
                int quantity = Integer.parseInt(quantityField.getText());

                // 创建新图书对象
                Book book = new Book(0, title, author, publisher, publishDate, isbn, quantity);
                bookService.addBook(book);

                // 清空输入框
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

        // 将控件添加到布局
        layout.getChildren().addAll(
                bookTitleField, queryButton,
                new Label("添加新书:"),
                titleField, authorField, publisherField, publishDatePicker, isbnField, quantityField, addButton
        );

        // 设置场景并显示
        Scene scene = new Scene(layout, 400, 500);
        primaryStage.setScene(scene);
        primaryStage.setTitle("管理员面板 - 书籍管理");
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}